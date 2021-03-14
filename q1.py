import pandas as pd
import re

s = pd.read_table('/home/clouduser/yelp_academic_dataset_business.tsv').city.dropna()
print(s[s.str.contains('las vegas', flags=re.IGNORECASE)].count())
