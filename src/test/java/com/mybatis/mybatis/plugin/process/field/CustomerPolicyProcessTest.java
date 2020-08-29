package com.mybatis.mybatis.plugin.process.field;

import com.mybatis.mybatis.plugin.RuleFieldCustomer;
import com.mybatis.mybatis.plugin.RuleFieldValueCustomer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CustomerPolicyProcessTest {

    @InjectMocks
    private CustomerPolicyProcess customerPolicyProcess;

    @MockBean
    private BeanFactory beanFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessFieldPolicy() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        String value = "myRuleFieldCustomer";
        String field = customerPolicyProcess.processFieldPolicy(value);
        assert field == null;

        CustomerPolicyProcess.fieldInstance.put(value, new MyRuleFieldCustomer());
        field = customerPolicyProcess.processFieldPolicy(value);
        assert field.equals("tenant_key");
    }

    @Test
    public void testProcessFieldValuePolicy() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = "myRuleFieldValueCustomer";

        String fieldValue = customerPolicyProcess.processFieldValuePolicy(value);
        assert fieldValue == null;

        CustomerPolicyProcess.fieldValueInstance.put(value, new MyRuleFieldValueCustomer());
        fieldValue = customerPolicyProcess.processFieldValuePolicy(value);
        assert fieldValue.equals("demo");

    }

    @Test
    public void checkCache() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = "myRuleFieldCustomer";
        CustomerPolicyProcess.fieldInstance.put(value, new MyRuleFieldCustomer());
        RuleFieldCustomer instance = customerPolicyProcess.checkCache(value, RuleFieldCustomer.class);
        assert instance instanceof MyRuleFieldCustomer;

        // class name
        value = MyRuleFieldCustomer.class.getName();
        CustomerPolicyProcess.fieldInstance.put(value, new MyRuleFieldCustomer());
        instance = customerPolicyProcess.checkCache(value, RuleFieldCustomer.class);
        assert instance instanceof MyRuleFieldCustomer;


        // name
        value = "myRuleFieldValueCustomer";
        CustomerPolicyProcess.fieldValueInstance.put(value, new MyRuleFieldValueCustomer());
        RuleFieldValueCustomer ruleFieldValueCustomer = customerPolicyProcess.checkCache(value, RuleFieldValueCustomer.class);
        assert ruleFieldValueCustomer instanceof MyRuleFieldValueCustomer;


        // class name
        value = MyRuleFieldValueCustomer.class.getName();
        CustomerPolicyProcess.fieldValueInstance.put(value, new MyRuleFieldValueCustomer());
        ruleFieldValueCustomer = customerPolicyProcess.checkCache(value, RuleFieldValueCustomer.class);
        assert ruleFieldValueCustomer instanceof MyRuleFieldValueCustomer;
    }

    @Test
    public void getInstanceByCache() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = "myRuleFieldCustomer";
        CustomerPolicyProcess.fieldInstance.put(value, new MyRuleFieldCustomer());
        RuleFieldCustomer instance = customerPolicyProcess.getInstance(value, RuleFieldCustomer.class);
        assert instance instanceof MyRuleFieldCustomer;
    }

    @Test
    public void getInstanceByBeanFactoryClass() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = "myRuleFieldCustomer";
        RuleFieldCustomer ruleFieldCustomer = new MyRuleFieldCustomer();
        Mockito.doReturn(ruleFieldCustomer).when(beanFactory).getBean(RuleFieldCustomer.class);

        RuleFieldCustomer instance = customerPolicyProcess.getInstance(value, RuleFieldCustomer.class);

        assert instance instanceof MyRuleFieldCustomer;
    }

    @Test
    public void getInstanceByBeanFactoryName() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = "myRuleFieldCustomer";
        RuleFieldCustomer ruleFieldCustomer = new MyRuleFieldCustomer();
        Mockito.doReturn(ruleFieldCustomer).when(beanFactory).getBean(value);

        RuleFieldCustomer instance = customerPolicyProcess.getInstance(value, RuleFieldCustomer.class);

        assert instance instanceof MyRuleFieldCustomer;
    }

    @Test
    public void getInstanceByBeanFactoryValueClass() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = MyRuleFieldCustomer.class.getName();
        RuleFieldCustomer ruleFieldCustomer = new MyRuleFieldCustomer();
        Mockito.doReturn(ruleFieldCustomer).when(beanFactory).getBean(MyRuleFieldCustomer.class);

        RuleFieldCustomer instance = customerPolicyProcess.getInstance(value, RuleFieldCustomer.class);

        assert instance instanceof MyRuleFieldCustomer;
    }

    @Test
    public void getInstanceByReflect() {
        CustomerPolicyProcess.fieldValueInstance.clear();
        CustomerPolicyProcess.fieldInstance.clear();

        // name
        String value = MyRuleFieldCustomer.class.getName();
        RuleFieldCustomer instance = customerPolicyProcess.getInstance(value, RuleFieldCustomer.class);
        assert instance instanceof MyRuleFieldCustomer;
    }

    @Test
    public void testSetBeanFactory() {
        customerPolicyProcess.setBeanFactory(this.beanFactory);
    }

}