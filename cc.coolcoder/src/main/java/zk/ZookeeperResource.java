package zk;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.AbstractResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ZookeeperResource extends AbstractResource implements ApplicationContextAware, DisposableBean {
    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     *
     * @throws Exception in case of shutdown errors.
     *                   Exceptions will get logged but not rethrown to allow
     *                   other beans to release their resources too.
     */
    @Override
    public void destroy() throws Exception {

    }

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     * <p>Implementations are also encouraged to return this value
     * from their {@code toString} method.
     *
     * @see Object#toString()
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * Return an {@link InputStream} for the content of an underlying resource.
     * <p>It is expected that each call creates a <i>fresh</i> stream.
     * <p>This requirement is particularly important when you consider an API such
     * as JavaMail, which needs to be able to read the stream multiple times when
     * creating mail attachments. For such a use case, it is <i>required</i>
     * that each {@code getInputStream()} call returns a fresh stream.
     *
     * @return the input stream for the underlying resource (must not be {@code null})
     * @throws FileNotFoundException if the underlying resource doesn't exist
     * @throws IOException           if the content stream could not be opened
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
}
