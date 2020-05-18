package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.demo.entities.MainEntity;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

import net.ttddyy.dsproxy.QueryCountHolder;

@DataJpaTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionalTestExecutionListener.class
})
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@TestPropertySource(properties = {
    "decorator.datasource.datasource-proxy.count-query=true",
    "spring.jpa.properties.hibernate.show_sql=true",
    "spring.jpa.properties.hibernate.format_sql=true"
})
@EntityScan(basePackageClasses = MainEntity.class)
@DatabaseTearDown("/teardown.xml")
@DatabaseSetup("/datasetup.xml")
public class MainRepositoryTest {

    @Autowired
    private MainRepository repository;

    @BeforeEach
    void reset() {
        QueryCountHolder.clear();
    }

    @Test
    void shouldSelectWithCustomQuery() {
        assertThat(repository.findByIdIn(Set.of(1L, 2L)))
            .hasSize(2);

        assertThat(QueryCountHolder.getGrandTotal().getSelect()).isEqualTo(1);
    }

}