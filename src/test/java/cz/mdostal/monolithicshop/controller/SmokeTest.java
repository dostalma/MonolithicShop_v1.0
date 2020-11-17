package cz.mdostal.monolithicshop.controller;

import cz.mdostal.monolithicshop.configuration.AppConfiguration;
import cz.mdostal.monolithicshop.configuration.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfiguration.class, AppConfiguration.class})
@WebAppConfiguration
public class SmokeTest {

    private

    @Autowired
    ProductController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
