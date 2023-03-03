import com.bank.controller.BankApplication;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        BankUtil.connection.setAutoCommit(false);
//        PreparedStatement ps = BankUtil.connection.prepareStatement("INSERT INTO mytable (id, name) VALUES (?, ?)");
//        ps.setInt(1, 1);
//        ps.setString(2, "value1");
//        ps.addBatch(); // add the statement to the batch
//        ps.executeBatch();
//        PreparedStatement ps2 = BankUtil.connection.prepareStatement("INSERT INTO mytable (id, name) VALUES (?, ?)");
//        ps2.setInt(1, 2);
//        ps2.setString(2, "value1");
//        ps2.addBatch(); // add the statement to the batch
//        ps2.executeBatch();
//        PreparedStatement ps3 = BankUtil.connection.prepareStatement("INSERT INTO mytable (id, name) VALUES (?, ?)");
//        ps3.setString(1, "sds");
//        ps3.setString(2, "value1");
//        ps3.addBatch(); // add the statement to the batch
////        ps3.executeUpdate();
//        ps3.executeBatch();
//        BankUtil.connection.commit();

        BankApplication app = new BankApplication();
        app.run();
//        Scanner scanner = new Scanner(System.in);
//        long x = 5256635566l;
//        String sh = "Shawky";
//        System.out.println(sh.indexOf("t"));
//        String name = "Shawky Ebrahim";
//        System.out.println(name.substring(2,5));
//        Integer[] arr = {5,9,1,4,3,2,8,7,2};
//        Arrays.sort(arr, (o1, o2) -> {
//            return - o1.compareTo(o2);
//        });
//        System.out.println(Arrays.toString(arr));
//        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
//        List<Integer> list2 = Arrays.asList(2,3);
//        System.out.println(list);
//        System.out.println(list2);
//        list.retainAll(list2);
//        System.out.println(list);
//        test<String> t = new test<>() {
//            @Override
//            public String print() {
//                name = "dsdsd";
//                return name;
//            }
//        };
//        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> {
//            if (a > b) return -1;
//            else if (a < b) return 1;
//            else return 0;
//        }) {{
//            add(24);add(50);add(60);add(52);add(19);
//        }};
        /*System.out.println(priorityQueue);
        for (Integer entry : priorityQueue) {
            System.out.println(entry);
        }*/

        // SQL as batches
//        try {
//            PreparedStatement preparedStatement = BankUtil.connection.prepareStatement("INSERT INTO mytable VALUES (?, ?)");
//            preparedStatement.setInt(1, 1);
//            preparedStatement.setString(2, "Shawky");
//            preparedStatement.addBatch();
//            preparedStatement.setInt(1, 2);
//            preparedStatement.setString(2, "Ebrahim");
//            preparedStatement.addBatch();
//            preparedStatement.setInt(1, 3);
//            preparedStatement.setString(2, "Ahmed");
//            preparedStatement.addBatch();
//            int[] rowsAffected = preparedStatement.executeBatch();
//        }catch (SQLException e){
//            System.out.println("There is an error when executing a query");
//        }finally {
//            connection.close();
//        }
//
//        Statement statement = BankUtil.connection.createStatement();
//        statement.executeUpdate("INSERT INTO mytable VALUES (1, 'Shawky')");
//        statement.executeUpdate("INSERT INTO mytable VALUES (2, 'Ebrahim')");
//        statement.executeUpdate("INSERT INTO mytable VALUES ('Ahmed', 3)");
//        statement.executeUpdate("INSERT INTO mytable VALUES (4, 'Mahmoud')");
//        try {
//            BankUtil.connection.setAutoCommit(false);
//            Statement statement = BankUtil.connection.createStatement();
//            statement.executeUpdate("INSERT INTO mytable VALUES (1, 'Shawky')");
//            statement.executeUpdate("INSERT INTO mytable VALUES (2, 'Ebrahim')");
//            statement.executeUpdate("INSERT INTO mytable VALUES ('Ahmed', 3)");
//            statement.executeUpdate("INSERT INTO mytable VALUES (4, 'Mahmoud')");
//            BankUtil.connection.commit();
//        } catch (SQLException e) {
//            BankUtil.connection.rollback();
//            System.out.println("These Queries have wrong syntax!");
//        } finally {
//            BankUtil.connection.setAutoCommit(true);
//            BankUtil.connection.close();
//        }
//        Statement statement = BankUtil.connection.createStatement();
//        BankUtil.connection.setAutoCommit(false);
//        statement.addBatch("insert into mytable values(2,'Shawky')");
//        statement.addBatch("insert into mytable values(3,'Ebrahim')");
//        statement.addBatch("insert into mytable values('Ahmed', 4)");
//        statement.addBatch("insert into mytable values(5,'Mahmoud')");
//        statement.executeBatch();
//        BankUtil.connection.commit();
    }

}

