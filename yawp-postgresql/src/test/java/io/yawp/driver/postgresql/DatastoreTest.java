package io.yawp.driver.postgresql;

import static org.junit.Assert.assertEquals;

import javax.naming.Context;

import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGConnectionPoolDataSource;

public class DatastoreTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, InitialContextMock.class.getName());

		PGConnectionPoolDataSource ds = new PGConnectionPoolDataSource();
		ds.setUrl("jdbc:postgresql://localhost/yawp_test");
		// ds.setUser("MY_USER_NAME");
		// ds.setPassword("MY_USER_PASSWORD");

		InitialContextMock.bind("jdbc/yawp_test", ds);
	}

	@Test
	public void testCreateRetrieveEntity() {
		// create table people (id bigserial primary key, entity jsonb);

		Entity entity = new Entity("people");
		entity.setProperty("name", "jim");

		Datastore datastore = new Datastore();
		datastore.put(entity);

		Entity retrievedEntity = datastore.get(entity.getKey());
		assertEquals("jim", retrievedEntity.getProperty("name"));
	}

	@Test
	public void testCreateUpdateEntity() {
		Entity entity = new Entity("people");
		entity.setProperty("name", "jim");

		Datastore datastore = new Datastore();
		datastore.put(entity);

		Key key = entity.getKey();

		entity.setProperty("name", "robert");
		datastore.put(entity);

		Entity retrievedEntity = datastore.get(key);
		assertEquals("robert", retrievedEntity.getProperty("name"));

	}

	@Test
	public void testForceName() {
		Key key = Key.create("people", "xpto");

		Entity entity = new Entity(key);
		entity.setProperty("name", "jim");

		Datastore datastore = new Datastore();
		datastore.put(entity);

		Entity retrievedEntity = datastore.get(key);
		assertEquals("jim", retrievedEntity.getProperty("name"));
	}

}
