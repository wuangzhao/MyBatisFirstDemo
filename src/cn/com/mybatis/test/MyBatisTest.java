package cn.com.mybatis.test;

import cn.com.mybatis.datasource.DataConnection;
import cn.com.mybatis.mapper.CustomerMapper;
import cn.com.mybatis.po.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBatisTest {
    public DataConnection dataConn = new DataConnection();

    @Test
    public void testSelect() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        User user = sqlSession.selectOne("test.findUserById", 1);
        System.out.println(user.getUsername());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(user.getBirthday()));
        sqlSession.close();

    }

    @Test
    public void testFuzzySearch() throws IOException{
        SqlSession sqlSession = dataConn.getSqlSession();
        List<User> userList = sqlSession.selectList("test.findUserByUsername", "丽");
        for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            System.out.println(u.getUsername());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(simpleDateFormat.format(u.getBirthday()));
        }
    }

    @Test
    public void testInsert() throws Exception{
        SqlSession sqlSession = dataConn.getSqlSession();
        User user = new User();
        user.setUsername("孙佳佳");
        user.setGender("女");
        user.setPassword("5555");
        user.setEmail("5555@126.com");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(simpleDateFormat.parse("1991-03-14"));
        user.setProvince("湖北省");
        user.setCity("武汉市");
        sqlSession.insert("test.insertUser", user);
        sqlSession.commit();
        System.out.println(user.getId());
        sqlSession.close();
    }

    @Test
    public void testDelete() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        sqlSession.delete("test.deleteUser", 5);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdate() throws IOException {
        SqlSession sqlSession =dataConn.getSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("张丽");
        sqlSession.update("test.updateUserName", user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testFindUserList() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        UserQueryInfo userQueryInfo = new UserQueryInfo();
        UserInstance userInstance = new UserInstance();
        userInstance.setGender("女");
        userInstance.setUsername("丽");
        userQueryInfo.setUserInstance(userInstance);

        List<UserInstance> userInstanceList = sqlSession.selectList("test.findUserList", userQueryInfo);
        for (int i = 0; i < userInstanceList.size(); i++) {
            UserInstance user = (UserInstance)userInstanceList.get(i);
            System.out.println("userId: " + user.getId() + " name: " + user.getUsername());
        }
    }

    @Test
    public void testBatchCustomer() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        List<BatchCustomer> bcList = sqlSession.selectList("test.findBatchCustomer");
        if (bcList != null) {
            BatchCustomer batchCustomer = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < bcList.size(); i++) {
                batchCustomer = bcList.get(i);
                System.out.println("卡号为" + batchCustomer.getAcno() + "的名为" + batchCustomer.getUsername() + "的客户: \n于" +
                        sdf.format(batchCustomer.getCreattime()) + "采购了批次为" + batchCustomer.getNumber() +
                        "的唯一理财");
            }
        }
        sqlSession.close();
    }

    @Test
    public void testBatchCustomerToMap() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        List<BatchItem> bcList = sqlSession.selectList("findBatchCustomerToMap");
        if (bcList != null) {
            BatchItem batchItem = null;
            Customer customer = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < bcList.size(); i++) {
                batchItem = bcList.get(i);
                customer = batchItem.getCustomer();
                System.out.println("卡号为"+customer.getAcno()+"的名为"
                        +customer.getUsername()+"的客户:\n于"
                        +sdf.format(batchItem.getCreatetime())+"采购了批次号为"
                        +batchItem.getNumber()+"的一批理财产品");
            }
        }
    }

    @Test
    public void testfindBatchAndBatchDetail() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();


        List<BatchItem> batchItemList = sqlSession.selectList("findBatchAndBatchDetail");
//        BatchItem batchItem = sqlSession.selectOne("findBatchAndBatchDetail");
        if (batchItemList != null) {
            BatchItem batchItem = null;
            for (int i = 0; i < batchItemList.size(); i++) {
                batchItem = batchItemList.get(i);
                if (batchItem != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Customer customer = batchItem.getCustomer();
                    List<BatchDetail> batchDetails = batchItem.getBatchDetails();
                    System.out.println("卡号为"+customer.getAcno()+"的名为"
                            +customer.getUsername()+"的客户:\n于"
                            +sdf.format(batchItem.getCreatetime())+"采购了批次号为"
                            +batchItem.getNumber()+"的一批理财产品，详情如下：");
                    BatchDetail batchDetail = null;
                    if (batchDetails != null) {
                        for (int j = 0; j < batchDetails.size(); j++) {
                            batchDetail = batchDetails.get(j);
                            System.out.println("id为"+batchDetail.getProduct_id()
                                    +"的理财产品"+batchDetail.getProduct_num()+"份");
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testfindCustomerAndProducts() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();

        List<Customer> customers = sqlSession.selectList("findUserAndProducts");
        if (customers != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Customer customer = null;
            for (int i = 0; i < customers.size(); i++) {
                customer = customers.get(i);
                System.out.println("卡号为"+customer.getAcno()+"的名为"
                        +customer.getUsername()+"的客户:");
                List<Batch> batchList = customer.getBatchList();
                Batch batch = null;
                for (int j = 0; j < batchList.size(); j++) {
                    batch = batchList.get(j);
                    System.out.println("于"
                            +sdf.format(batch.getCreatetime())+"采购了批次号为"
                            +batch.getNumber()+"的一批理财产品，详情如下：");
                    List<BatchDetail> batchDetails = batch.getBatchDetailList();
                    BatchDetail batchDetail = null;
                    for (int k = 0; k < batchDetails.size(); k++) {
                        batchDetail = batchDetails.get(k);
                        System.out.println("id为"+batchDetail.getProduct_id()
                                +"的理财产品"+batchDetail.getProduct_num()+"份。");
                        //4.获取每个批次明细中的理财产品详细信息
                        FinacialProduct  finacialProduct = batchDetail.getFinacialProduct();
                        System.out.println("该理财产品的详细信息为：\n"
                                +"产品名称:"+finacialProduct.getName()
                                +"|产品价格:"+finacialProduct.getPrice()
                                +"|产品简介:"+finacialProduct.getDetail());
                    }
                }
            }
        }
    }

    @Test
    public void testFindBatchCustomerLazyLoading() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();

        List<BatchItem> batchItemList = sqlSession.selectList("findBatchUserLazyLoading");
        BatchItem batchItem = null;
        Customer customer = null;
        if (batchItemList != null) {
            for (int i = 0; i < batchItemList.size(); i++) {
                batchItem = batchItemList.get(i);
                System.out.println("订单编号" + batchItem.getNumber());
                customer = batchItem.getCustomer();
                System.out.println("订购用户姓名" + customer.getUsername());
            }
        }
    }

    @Test
    public void testFindCustomerOnMapper() throws Exception {
        SqlSession sqlSession = dataConn.getSqlSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
        Customer customer = customerMapper.findCustomerById(1);
        System.out.println("用户姓名：" + customer.getUsername());
        sqlSession.close();
    }

    @Test
    public void testFindCustomerCache1() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        Customer customer1 = sqlSession.selectOne("test.findCustomerById", 1);
        System.out.println("用户姓名： " + customer1.getUsername());
        Customer customer2 = sqlSession.selectOne("test.findCustomerById", 1);
        System.out.println("用户姓名： " + customer2.getUsername());
        sqlSession.close();
    }
}