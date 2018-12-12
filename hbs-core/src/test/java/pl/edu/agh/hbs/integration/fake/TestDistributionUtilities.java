package pl.edu.agh.hbs.integration.fake;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import pl.edu.agh.age.compute.api.DistributionUtilities;

import java.util.HashSet;
import java.util.Set;

public class TestDistributionUtilities implements DistributionUtilities {
    private final HazelcastInstance hazelcastInstance;
    private final Set<DistributedObject> distributedObjects = new HashSet<>(10);

    public TestDistributionUtilities(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public <K, V> IMap<K, V> getMap(String name) {
        IMap<K, V> map = this.hazelcastInstance.getMap("compute/" + name);
        this.distributedObjects.add(map);
        return map;
    }

    @Override
    public IdGenerator getIdGenerator(String name) {
        IdGenerator idGenerator = this.hazelcastInstance.getIdGenerator("compute/" + name);
        this.distributedObjects.add(idGenerator);
        return idGenerator;
    }

    @Override
    public void reset() {
        this.distributedObjects.forEach(DistributedObject::destroy);
        this.distributedObjects.clear();
    }
}
