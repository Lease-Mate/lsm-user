package com.lsm.ws.user.configuration.cache;

import com.lsm.ws.user.configuration.properties.CacheProperties;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CacheProperties.class})
public class IgniteCacheConfiguration {

    private final String WORK_DIR = System.getProperty("user.dir") + "/work";

    private final CacheProperties cacheProperties;

    public IgniteCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    public Ignite ignite() {
        return Ignition.start(igniteConfiguration());
    }

    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setGridLogger(new Slf4jLogger());
        return cfg.setWorkDirectory(WORK_DIR)
                  .setDiscoverySpi(tcpDiscoverySpi())
                  .setCommunicationSpi(tcpCommunicationSpi());
    }

    private TcpCommunicationSpi tcpCommunicationSpi() {
        var communicationSpi = new TcpCommunicationSpi();
        communicationSpi.setLocalPort(cacheProperties.getCommunicationPort());
        communicationSpi.setLocalPortRange(0);
        return communicationSpi;
    }

    private TcpDiscoverySpi tcpDiscoverySpi() {
        TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
        var tcpDiscoveryIpFinder = new TcpDiscoveryVmIpFinder();
        tcpDiscoveryIpFinder.setAddresses(cacheProperties.getAddresses());
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryIpFinder);
        tcpDiscoverySpi.setLocalPort(cacheProperties.getDiscoveryPort());
        tcpDiscoverySpi.setLocalPortRange(0);
        return tcpDiscoverySpi;
    }
}
