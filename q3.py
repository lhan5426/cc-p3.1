
  # * pandas:
  # Pandas can merge DataFrame objects by performing a database-style join
  # operation by columns or indexes.
  # There're different types of join options in Pandas: ‘left’, ‘right’, 
  # ‘outer’, ‘inner’, like in other databases, and the default join is ‘left’.
  # e.g. Join A and B on column key in Pandas will be
import pandas as pd

A = pd.read_table('/home/clouduser/yelp_academic_dataset_review.tsv').drop_duplicates(subset=["user_id"])
B = pd.read_table('/home/clouduser/yelp_academic_dataset_tip.tsv').drop_duplicates(subset=["user_id"])
print(len(pd.merge(A, B, how='inner', on="user_id").drop_duplicates(subset=["user_id"])))
