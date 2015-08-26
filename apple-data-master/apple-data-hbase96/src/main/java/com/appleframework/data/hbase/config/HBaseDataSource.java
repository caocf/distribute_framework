package com.appleframework.data.hbase.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import com.appleframework.data.hbase.exception.SimpleHBaseException;
import com.appleframework.data.hbase.util.ConfigUtil;
import com.appleframework.data.hbase.util.StringUtil;
import com.appleframework.data.hbase.util.TableNameUtil;
import com.appleframework.data.hbase.util.Util;

/**
 * HbaseDataSource represent one hbase data source.
 * 
 * @author xinzhi
 * */
@SuppressWarnings("deprecation")
public class HBaseDataSource {

    /** log. */
    final private static Logger log              = Logger.getLogger(HBaseDataSource.class);
    //----------config--------------
    /**
     * dataSource id.
     * */
    @ConfigAttr
    private String              id;
    /**
     * hbase's config resources, such as hbase zk config.
     * */
    @ConfigAttr
    private List<Resource>      hbaseConfigResources;

    //---------------------------runtime-------------------------
    /**
     * final hbase's config item.
     * */
    private Map<String, String> finalHbaseConfig = new HashMap<String, String>();
    
    /**
     * final hbase's config item.
     * */
    private Properties hbaseProperties = new Properties();

    /**
     * hbase Configuration.
     * */
    private Configuration       hbaseConfiguration;
    
    private HTableInterfaceFactory tableFactory;
    
	private HTablePool tablePool;
    
    private Integer tablePoolMaxSize = 10;

    /**
     * init dataSource.
     * */
	public void init() {
        try {

            System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
            System.setProperty("javax.xml.parsers.SAXParserFactory",
                    "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");

            initHbaseConfiguration();
            
            tablePool = new HTablePool(hbaseConfiguration, tablePoolMaxSize);
            tableFactory = new PooledHTableFactory(tablePool);

            log.info(this);

        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    /**
     * Get HTableInterface by table Name.
     * 
     * @param tableName tableName.
     * @return HTableInterface.
     * */
    public HTableInterface getHTable(String tableName) {
        Util.checkEmptyString(tableName);
        try {
        	if (tableFactory != null) {
    			return tableFactory.createHTableInterface(hbaseConfiguration, tableName.getBytes("UTF-8"));
    		}
    		else {
    			return new HTable(hbaseConfiguration, TableNameUtil.getTableName(tableName));
    		}
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }
    
    /**
     * Get one HBaseAdmin.
     * */
    public HBaseAdmin getHBaseAdmin() {
        try {
            return new HBaseAdmin(hbaseConfiguration);
        } catch (Exception e) {
            log.error(e);
            throw new SimpleHBaseException(e);
        }
    }

    /**
     * init HbaseConfiguration
     * */
    private void initHbaseConfiguration() {
        try {
            if (hbaseConfigResources != null) {
                for (Resource resource : hbaseConfigResources) {
                    finalHbaseConfig.putAll(ConfigUtil.loadConfigFile(resource.getInputStream()));
                }
            }
            
            if (hbaseProperties != null) {
                Enumeration<?> en = hbaseProperties.propertyNames();
                while (en.hasMoreElements()) {
                	String key = (String) en.nextElement();
                	String Property = hbaseProperties.getProperty (key);
                	finalHbaseConfig.put(key, Property);
                }
            }
            
            hbaseConfiguration = HBaseConfiguration.create();
            for (Map.Entry<String, String> entry : finalHbaseConfig.entrySet()) {
                hbaseConfiguration.set(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            log.error("parseConfig error.", e);
            throw new SimpleHBaseException("parseConfig error.", e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Configuration getHbaseConfiguration() {
        return hbaseConfiguration;
    }

    public List<Resource> getHbaseConfigResources() {
        return hbaseConfigResources;
    }

    public void setHbaseConfigResources(List<Resource> hbaseConfigResources) {
        this.hbaseConfigResources = hbaseConfigResources;
    }

    public Properties getHbaseProperties() {
		return hbaseProperties;
	}

	public void setHbaseProperties(Properties hbaseProperties) {
		this.hbaseProperties = hbaseProperties;
	}

	public HTableInterfaceFactory getTableFactory() {
		return tableFactory;
	}

	public void setTableFactory(HTableInterfaceFactory tableFactory) {
		this.tableFactory = tableFactory;
	}

	public HTablePool getTablePool() {
		return tablePool;
	}

	public void setTablePool(HTablePool tablePool) {
		this.tablePool = tablePool;
	}

	public void setTablePoolMaxSize(Integer tablePoolMaxSize) {
		this.tablePoolMaxSize = tablePoolMaxSize;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------datasource--------------------------\n");
        StringUtil.append(sb, "#id#", id);
        StringUtil.append(sb, "#finalHbaseConfig#", finalHbaseConfig);
        sb.append("---------------datasource--------------------------\n");
        return sb.toString();
    }

}
