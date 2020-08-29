package com.mybatis.mybatis.plugin.process;

import com.mybatis.mybatis.plugin.config.MybatisPluginsConfig;
import com.mybatis.mybatis.plugin.config.PluginConfig;
import com.mybatis.mybatis.plugin.config.PluginRule;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @program: mybatis plugin
 * @description:
 * @author: lengrongfu
 * @created: 2020/08/14 23:25
 */
public class PluginsProcessImpl implements PluginsProcess {

    private static final Logger logger = LoggerFactory.getLogger(PluginsProcessImpl.class);

    private MybatisPluginsConfig mybatisPluginsConfig;

    private RuleProcess ruleProcess;

    public PluginsProcessImpl(MybatisPluginsConfig mybatisPluginsConfig, RuleProcess ruleProcess) {
        this.mybatisPluginsConfig = mybatisPluginsConfig;
        this.ruleProcess = ruleProcess;
    }

    /**
     * @param oldSql
     * @Description: 插件处理器实现类
     * @return:
     * @Creator: lengrongfu
     * @Date: 2020/8/14 11:25 下午
     */
    @Override
    public String pluginsProcess(String oldSql) {
        if (Objects.isNull(mybatisPluginsConfig) || CollectionUtils.isEmpty(mybatisPluginsConfig.getPlugins())) {
            return oldSql;
        }
        List<PluginConfig> plugins = mybatisPluginsConfig.getPlugins();
        Collections.sort(plugins);
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(oldSql);
        } catch (JSQLParserException e) {
            logger.error(e.getLocalizedMessage());
        }
        for (PluginConfig plugin : plugins) {
            Boolean pluginConfPass = PluginLevelValidate.DEFAULT.validateLevel(plugin, statement);
            if (logger.isDebugEnabled()) {
                logger.info("{} plugin level validate result {}", plugin.getName(), pluginConfPass);
            }
            if (!pluginConfPass) {
                logger.info("{} plugin level validate not pass", plugin.getName());
                continue;
            }
            List<PluginRule> rules = plugin.getRules();
            Collections.sort(rules);

            for (PluginRule rule : rules) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} plugin {} rule one handler", plugin.getName(), rule.getName());
                }
                ruleProcess.ruleProcess(statement, rule);
            }
        }
        return statement.toString();
    }
}
