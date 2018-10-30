package zk;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

public class ZookeeperConfigurer extends PropertySourcesPlaceholderConfigurer {

    private Map<String, Object> ctxPropsMap = new HashMap<>();

    /**
     * 配置路径
     */
    private ZookeeperResource zkLocation;

    private Resource[] localLocations = new Resource[0];

    public ZookeeperConfigurer() {
    }

    @Override
    public void setLocation(Resource location) {
        this.zkLocation = (ZookeeperResource) location;
        super.setLocations(mergeArray(this.localLocations, this.zkLocation));

    }

    private static Resource[] mergeArray(Resource[] m1, Resource m2) {
        Resource[] all = new Resource[m1.length + 1];
        if (m1.length > 0) {
            System.arraycopy(all, 0, m1, 0, m1.length);
            all[m1.length] = m2;
        } else {
            all[m1.length] = m2;
        }

        return all;
    }
}
