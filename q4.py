import MySQLdb
import pandas as pd
import os
import sys

conn = MySQLdb.connect(passwd=os.getenv('MYSQL_PWD'), db='yelp_db') # build the MySQL connection
query = "SELECT review_count, stars FROM businesses" # the SQL query
df = pd.read_sql(query, con=conn)
df.describe().to_csv(sys.stdout, encoding='utf-8', float_format='%.2f')
