package io.xiaper.jpa.util.id;

/**
 *
 * @author bytedesk.com
 */
public class DefaultIdGeneratorConfig implements IdGeneratorConfig{

    @Override
    public String getSplitString() {
        return "";
    }

    @Override
    public int getInitial() {
        return 1;
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public int getRollingInterval() {
        return 1;
    }

}
