import os
from os import path

import pandas as pd
import numpy as np
import re

pd.set_option('display.max_columns', 5000)
df = pd.read_csv("data/strat_litho_overview-xlsx.csv")

if not path.isdir("output"):
    os.mkdir("output")

lithostrat = []
content = []

exception_row = []
term_to_unifyterm_dict = {}
dict = {}


def transform(content):
    distinct_title = []
    line = content.split("^ ^")  # split each div(either title or content)
    "process the source content"
    if " ^  • " in line[len(line) - 1]:
        source = line[len(line) - 1].split(" ^  • ")[1]
        line[len(line) - 1] = line[len(line) - 1].split(" ^  • ")[0]
        line.append(source)
    index = 0
    title = []  # name, age...
    description = []  # long description
    "append the title and description into list"
    for i in line:
        if index % 2 != 0:
            description.append(i)
        else:
            title.append(i)
        index += 1

    pairs = []
    for i in range(len(title)):

        if len(title) != len(description):
            continue
        else:
            # continue
            if title[i] not in distinct_title:
                distinct_title.append(title[i])
            pair = (title[i], description[i])
            pairs.append(pair)

    return pairs
    # print(dict)


# process every row in dataframe
for row in range(df.shape[0]):

    if len(df["Description"][row]) > 20:

        lithostrat = df["Description"][row].split("^^")[0]
        content = df["Description"][row].split("^^")[1]  # remove the first element
        pairs = transform(content)
        dict[lithostrat] = pairs

    else:
        exception_row.append(row)

# dict to csv and clean
i = 0

term = pd.read_csv("data/terminology_standard.csv")  # read unify term into dict

for idx in range(len(term)):
    old_title = term.iloc[idx, 0]
    to_title = term.iloc[idx, 1]
    term_to_unifyterm_dict[old_title] = to_title  # put the term, unify terms

for lithostrat_info in dict:

    title_to_file = []
    content_to_file = []

    for i in dict.get(lithostrat_info):
        old_title = i[0].strip()
        if old_title in term_to_unifyterm_dict:
            title = term_to_unifyterm_dict.get(old_title)  # map the term to our term
        else:
            title = "(N/A)"
        content = i[1]
        title_to_file.append(title)
        content_to_file.append(content)

    df = pd.DataFrame({
        'ID': lithostrat_info,
        'title': title_to_file,
        'content': content_to_file
    })
    csv_file_name = "output/database.csv"

    if not os.path.isfile(csv_file_name):
        df.to_csv(csv_file_name)
    else:
        df.to_csv(csv_file_name, mode='a', header=False)

# Formation,/t,ID,/t,Group,/t,Name,/t,Lithology,/t,Period



def parse_group():
    pd.set_option('display.max_columns', 5000)
    df = pd.read_csv("data/strat_litho_overview-xlsx.csv")

    unit = []
    unit_parent = []
    for row in range(df.shape[0]):
        unit.append(
            str(df.loc[row, "Lithostrat. unit"]).replace("GP", "group").replace("FM", "formation").replace("MBR",
                                                                                                           "member").lower())

        unit_parent.append(
            str(df.loc[row, "Lithostrat. unit, parent"]).replace("GP", "group").replace("FM", "formation").replace(
                "MBR", "member").lower())

    df = pd.DataFrame({
        'unit': unit,
        'unit_parent': unit_parent,
    })
    return df


def parse_to_database():
    db = pd.read_csv("output/database.csv")

    Formations_dict = {}  # name:[formation, id, group(member), name, lithology, period]
    for row in range(db.shape[0]):
        formation_name = db["ID"][row]
        ID = formation_name.lower()
        if ID not in Formations_dict:
            Formations_dict[ID] = [formation_name, ID, "nan", formation_name,
                                   "lithology",
                                   "period"]
        else:
            if db["title"][row] == "Lithology":
                lithology = db["content"][row]
                Formations_dict[ID][4] = lithology
            elif db["title"][row] == "Age":
                period = db["content"][row]
                Formations_dict[ID][5] = period

    # add group(unit's parent) data
    unit_parent = parse_group()
    for row in range(unit_parent.shape[0]):
        # print(unit_parent["unit"][row])
        unit_name = unit_parent["unit"][row]
        if unit_name in Formations_dict:
            group = unit_parent["unit_parent"][row]
            Formations_dict[unit_name][2] = group

    for data in Formations_dict:
        output = Formations_dict.get(data)
        formation_name = output[0]
        ID = output[1]
        group = output[2]
        name = output[3]
        lithology = output[4]
        period = output[5]

        if group == "nan":
            group = ""

        df = pd.DataFrame({
            'Formation': formation_name,
            'ID': ID,
            'Group': group,
            "Name": name,
            "Lithology": lithology,
            "Period": period

        }, index=[0])
        csv_file_name = "output/database_to_be_used.csv"

        if not os.path.isfile(csv_file_name):
            df.to_csv(csv_file_name)
        else:
            df.to_csv(csv_file_name, mode='a', header=False)

parse_to_database()