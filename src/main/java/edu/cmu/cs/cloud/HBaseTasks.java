package edu.cmu.cs.cloud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HBaseTasks {
    /**
     * TODO: set the private IP address(es) of HBase zookeeper nodes.
     */
    private static String zkPrivateIPs = "<Comma-separated HBase Zookeeper private IPs>";
    /**
     * The name of your HBase table.
     */
    private static TableName tableName = TableName.valueOf("business");
    /**
     * HTable handler.
     */
    private static Table bizTable;
    /**
     * HBase connection.
     */
    private static Connection conn;
    /**
     * HBase configuration.
     */
    private static Configuration conf;
    /**
     * Byte representation of column family.
     */
    private static byte[] bColFamily = Bytes.toBytes("data");
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getRootLogger();

    /**
     * Initialize HBase connection.
     *
     * @throws IOException if a network exception occurs.
     */
    private static void initializeConnection() throws IOException {
        // Turn of the logging to get rid of unnecessary standard output.
        LOGGER.setLevel(Level.OFF);
        if (!zkPrivateIPs.matches("\\d+.\\d+.\\d+.\\d+(,\\d+.\\d+.\\d+.\\d+)*")) {
            System.out.print("Malformed HBase IP address");
            System.exit(-1);
        }
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkPrivateIPs);
        conf.set("hbase.zookeeper.property.clientport", "2181");
        conf.set("hbase.cluster.distributed", "true");
        conf.set("zookeeper.znode.parent","/hbase-unsecure");
        conn = ConnectionFactory.createConnection(conf);
        bizTable = conn.getTable(tableName);
    }

    /**
     * Clean up resources.
     *
     * @throws IOException
     * Throw IOEXception
     */
    private static void cleanup() throws IOException {
        if (bizTable != null) {
            bizTable.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * You should complete the missing parts in the following method.
     * Feel free to add helper functions if necessary.
     *
     * For all questions, output your answer in ONE single line,
     * i.e. use System.out.print().
     *
     * @param args The arguments for main method.
     * @throws IOException if a remote or network exception occurs.
     */
    public static void main(String[] args) throws IOException {
        initializeConnection();
        switch (args[0]) {
            case "demo":
                demo();
                break;
            case "rowkey-design":
                rowKeyDesignTask();
                break;
            case "q12":
                q12();
                break;
            case "q13":
                q13();
                break;
            case "q14":
                q14();
                break;
            case "q15":
                q15();
                break;
            default:
                break;
        }
        cleanup();
    }

    /**
     * This is a demo of how to use HBase Java API.
     * It will print the number of businesses in "Pittsburgh".
     *
     * @throws IOException if a remote or network exception occurs.
     */
    private static void demo() throws IOException {
        Scan scan = new Scan();
        byte[] bCol = Bytes.toBytes("city");
        scan.addColumn(bColFamily, bCol);
        SubstringComparator comp = new SubstringComparator("Pittsburgh");
        Filter filter = new SingleColumnValueFilter(
                bColFamily, bCol, CompareFilter.CompareOp.EQUAL, comp);
        scan.setFilter(filter);
        ResultScanner rs = bizTable.getScanner(scan);
        int count = 0;
        for (Result r = rs.next(); r != null; r = rs.next()) {
            count++;
        }
        System.out.println("Scan finished. " + count + " match(es) found.");
        rs.close();
    }

     /**
     * RowKey Design Task
     *
     * Scenario:
     * You are required to pre-split the table into 10 regions before loading the data.
     * This task shows how data is distributed among those 10 regions.
     *
     * Print the number of rows in each region, separate by comma.
     * An example of return value is "1,2,3,4,5,6,7,8,9,10".
     *
     * Note:
     * 1. You need to design the rowKey to evenly distribute the records among 10 regions.
     * 2. Do not forget to create design.pdf to explain your rowkey design.
     *
     * Hint:
     * 1. The class "org.apache.hadoop.hbase.client.Admin" may help you.
     * 2. You can scan the whole table by using startKey and endKey to get the row count.
     *
     * You are allowed to make changes such as modifying method name, parameter
     * list and/or return type.
     *
     */
    private static void rowKeyDesignTask() {
    }

    /**
     * Question 12.
     *
     * Scenario:
     * What's that new "Asian Fusion" place in "Shadyside" with free wifi and
     * bike parking?
     *
     * Print each name of the business on a single line.
     * If there are multiple answers, print all of them.
     *
     * Note:
     * 1. The "neighborhood" column should contain "Shadyside" as a substring.
     * 2. The "categories" column should contain "Asian Fusion" as a substring.
     * 3. The "WiFi" and "BikeParking" information can be found in the
     * "attributes" column. Please be careful about the format of the data.
     *
     * You are allowed to make changes such as modifying method name, parameter
     * list and/or return type.
     */
    private static void q12() {

    }

    /**
     * Question 13.
     *
     * Scenario:
     * I'm looking for some Indian food to eat in Downtown or Oakland of Pittsburgh
     * that start serving on Fridays at 5pm, but still deliver in case I'm too lazy
     * to drive there.
     *
     * Print each name of the business on a single line.
     * If there are multiple answers, print all of them 
     * in ascending lexicographical order.
     *
     * Note:
     * 1. The "name" column should contain "India" as a substring.
     * 2. The "neighborhood" column should contain "Downtown" or "Oakland"
     * as a substring.
     * 3. The "city" column should contain "Pittsburgh" as a substring.
     * 4. The "hours" column shows the hours when businesses start serving.
     * 5. The "RestaurantsDelivery" information can be found in the
     * "attributes" column.
     *
     * Hint:
     * You may consider using other comparators in the filter.
     *
     * You are allowed to make changes such as modifying method name, parameter
     * list and/or return type.
     */
    private static void q13() {

    }

    /**
     * Question 14.
     *
     * Write HBase query to do the equivalent of the SQL query:
     * SELECT name FROM businesses where business_id = "I1vE5o98Wy5pCULJoEclqw"
     * 
     * Note:
     * The rowkey is not the business_id since you design the new rowkey to avoid hotspotting
     * 
     * Hint:
     * You may consider using other HBase operations which are used to search
     * and retrieve one single row by the rowkey.
     *
     * You are allowed to make changes such as modifying method name, parameter
     * list and/or return type.
     */
    private static void q14() {

    }

    /**
     * Question 15.
     *
     * Write HBase query to do the equivalent of the SQL query:
     * SELECT COUNT(*) FROM businesses
     *
     * Print the number on a single line.
     *
     * Note:
     * 1. You are not allowed to scan the whole table and count the number of records.
     * 
     * 2 HBase uses Coprocessor to perform data aggregation across multiple
     * region servers, you need to enable Coprocessors inside HBase shell
     * before writing Java code.
     *
     *   Step 1. disable the table
     *   hbase> disable 'mytable'
     *
     *   Step 2. add the coprocessor
     *   hbase> alter 'mytable', METHOD =>
     *     'table_att','coprocessor'=>
     *     '|org.apache.hadoop.hbase.coprocessor.AggregateImplementation||'
     *
     *   Step 3. re-enable the table
     *   hbase> enable 'mytable'
     *
     * 3. You may want to look at the AggregationClient Class in HBase APIs.
     * 
     * 4. Since HBase stores data sparsely, you should make sure you use the correct API
     * in {@link org.apache.hadoop.hbase.client.Scan} to count the rows 
     * instead of counting the number of cells in one column (which could work with SQL Databases).
     * In HBase, it is common that a single column
     * might not have data in some of the rows. This can result in a wrong count
     * for the total number of rows. It is important that you read and understand
     * the difference between the following apis and select the correct one.
     * {@link org.apache.hadoop.hbase.client.Scan#addColumn}.
     * {@link org.apache.hadoop.hbase.client.Scan#addFamily}.
     *
     * You are allowed to make changes such as modifying method name, parameter
     * list and/or return type.
     */
    private static void q15() {

    }

}
