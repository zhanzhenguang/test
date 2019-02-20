package com.it.zzg.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ShardingConfig {

//    @Bean
//    public DataSource dataSource() throws Exception {
//        Resource resource = new ClassPathResource("sharding.yml");
//        File file = resource.getFile();
//        DataSource dataSource = new YamlShardingDataSource(file);
//        return dataSource;
//    }
	
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        
////        sqlSessionFactoryBean.setTypeAliasesPackage("com.it.zzg.model");
//        
////        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
////        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/*.xml"));
//        
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//    
//    @Bean
//    public PlatformTransactionManager transactionManager() throws Exception {
//        return new DataSourceTransactionManager(dataSource());
//    }
}
