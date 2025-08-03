package org.danji.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:/application.properties"})
@MapperScan(basePackages = {"org.danji.board.mapper",
        "org.danji.member.mapper",
        "org.danji.travel.mapper",
        "org.danji.mapper",
        "org.danji.availableMerchant.mapper",
        "org.danji.mapper",
        "org.danji.wallet.mapper",
        "org.danji.transaction.mapper",
        "org.danji.localCurrency.mapper",
        "org.danji.cashback.mapper",
        "org.danji.region.mapper"
})
@ComponentScan(basePackages = {"org.danji.board.service",
        "org.danji.member.service",
        "org.danji.travel.service",
        "org.danji.availableMerchant.service",
        "org.danji.availableMerchant.util",
        "org.danji.transaction.service",
        "org.danji.cashback.converter",
        "org.danji.transaction.converter",
        "org.danji.region.service",
        "org.danji.localCurrency.service",
        "org.danji.transaction.processor",
        "org.danji.wallet.service",
        "org.danji.transaction.strategy",
        "org.danji.batch"
})
@Log4j2
@EnableTransactionManagement
public class RootConfig {

    @Value("${jdbc.driver}")
    String driver;
    @Value("${jdbc.url}")
    String url;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;
    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfigLocation(
                applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactory.setDataSource(dataSource());

        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager(dataSource());

        return manager;
    }

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineVersion("0.0")  // 초기 마이그레이션 버전 설정
                .baselineOnMigrate(true)
                .encoding("UTF-8")
                .cleanDisabled(true)  // 안전을 위해 clean 비활성화
                .validateMigrationNaming(true)
                .load();
        // Flyway 설정

        // 수동으로 migrate 호출
        flyway.migrate();

        return flyway;
    }

}
