////FIXME: For some reasons main is not working...
////TODO: Documentation is not very good regarding main method bootstrap
//TODO: https://docs.thorntail.io/2018.5.0/
//TODO: https://docs.thorntail.io/2018.5.0/#maven-plugin-properties
//package daggerok;
//
//import daggerok.commands.CommandGateway;
//import daggerok.events.EventStore;
//import daggerok.filters.CrossOriginResourceFilter;
//import daggerok.rest.MessageCommandResource;
//import daggerok.rest.MessageQueryResource;
//import lombok.SneakyThrows;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.wildfly.swarm.Swarm;
//import org.wildfly.swarm.datasources.DatasourcesFraction;
//import org.wildfly.swarm.flyway.FlywayFraction;
//import org.wildfly.swarm.jaxrs.JAXRSArchive;
//import org.wildfly.swarm.spi.api.Fraction;
//
//public class Main {
//
//  @SneakyThrows
//  public static void main(String[] args) {
//    new Swarm()
//        .fraction(new DatasourcesFraction()
//                      .jdbcDriver("h2", driver -> {
//                        driver.driverClassName("org.h2.Driver");
//                        driver.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
//                        driver.driverModuleName("com.h2database.h2");
//                      })
//                      .dataSource("ExampleDS", dataSource -> {
//                        dataSource.driverName("h2");
//                        dataSource.connectionUrl("jdbc:h2:mem:wildfly-swarm;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//                        dataSource.userName("wildfly-swarm");
//                        dataSource.password("wildfly-swarm");
//                      }))
//        .start()
//        .deploy(ShrinkWrap.create(JAXRSArchive.class)
//                          .addPackages(true,
//                                       CommandGateway.class.getPackage(),
//                                       EventStore.class.getPackage(),
//                                       CrossOriginResourceFilter.class.getPackage(),
//                                       MessageCommandResource.class.getPackage()
//
//                          ));
//  }
//
////  @SneakyThrows
////  public static void main(String[] args) {
////    new Swarm()// data sources (h2)
////               .fraction(dataSourcesFraction())
////               // flyway
////               .fraction(flywayFraction())
////               // start swarm
////               .start(deployment());
////  }
////
////  static Archive<JAXRSArchive> deployment() {
////    final JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class);
////    archive.addPackages(true, RestApplication.class.getPackage());
////    archive.addClasses(
////        MessageQueryResource.class,
////        MessageCommandResource.class,
////        CrossOriginResourceFilter.class,
////        EventStore.class,
////        CommandGateway.class
////    );
////    return archive;
////  }
////
////  static Fraction<FlywayFraction> flywayFraction() {
////    return new FlywayFraction()
////        .jdbcUrl("jdbc:h2:mem:wildfly-swarm;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
////        .jdbcUser("wildfly-swarm")
////        .jdbcPassword("wildfly-swarm");
////  }
////
////  static Fraction<DatasourcesFraction> dataSourcesFraction() {
////    return new DatasourcesFraction()
////        .jdbcDriver("h2", (d) -> {
////          d.driverClassName("org.h2.Driver");
////          d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
////          d.driverModuleName("com.h2database.h2");
////        })
////        .dataSource("WildFlySwarmDS", (ds) -> {
////          ds.driverName("h2");
////          ds.connectionUrl("jdbc:h2:mem:wildfly-swarm;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
////          ds.userName("wildfly-swarm");
////          ds.password("wildfly-swarm");
////        });
////  }
//}
