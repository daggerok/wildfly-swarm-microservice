package daggerok;

import lombok.SneakyThrows;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

@DefaultDeployment(type = DefaultDeployment.Type.WAR)
@RunWith(Arquillian.class)
public class InContainerTest {

  @ArquillianResource
  InitialContext context;

  @Test
  public void testDataSourceIsBound() throws Exception {
    DataSource ds = (DataSource) context.lookup("java:jboss/datasources/MyDataSource");
    System.out.println(ds.getConnection());
    System.out.println(ds.getConnection().isReadOnly());
    assertNotNull(ds);
  }

//  @Deployment
//  @SneakyThrows
//  public static JavaArchive createDeployment() {
//    return ShrinkWrap.create(JavaArchive.class, "test-wildfly-swarm-microservice.war")
//                     .addPackages(true, RestApplication.class.getPackage())
//                     .addAsResource(EmptyAsset.INSTANCE, "project-testing.yml")
//                     .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//        ;
//  }
}
