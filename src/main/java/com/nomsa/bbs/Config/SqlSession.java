package com.nomsa.bbs.Config;

import com.nomsa.bbs.Model.FileModel;
import com.nomsa.bbs.Model.PostModel;
import com.nomsa.bbs.Model.ReplyModel;
import com.nomsa.bbs.Model.UserModel;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class SqlSession {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(res);

        sqlSessionFactoryBean.setTypeAliases(new Class<?>[] { UserModel.class, PostModel.class, ReplyModel.class, FileModel.class});

        return sqlSessionFactoryBean.getObject();

    }

}
