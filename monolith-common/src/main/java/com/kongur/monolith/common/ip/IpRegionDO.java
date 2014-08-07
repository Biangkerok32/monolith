package com.kongur.monolith.common.ip;


/**
 * ip ��ַ
 * 
 * @author zhengwei
 */
public class IpRegionDO extends com.kongur.monolith.common.DomainBase {

    /**
     * 
     */
    private static final long serialVersionUID = 5458935713585348466L;

    /**
     * ���� eg �й�
     */
    private String            country;

    /**
     * ����eg ����
     */
    private String            area;

    /**
     * ʡ eg �㽭ʡ
     */
    private String            region;

    /**
     * �� eg ������
     */
    private String            city;

    /**
     * ���Ҷ�Ӧ��id
     */
    private String            countryId;

    /**
     * �����Ӧ��id
     */
    private String            areaId;

    /**
     * ʡ��Ӧ��id
     */
    private String            regionId;

    /**
     * �ж�Ӧ��id
     */
    private String            cityId;

    public IpRegionDO(String country, String area, String region, String city) {
        this.country = country;
        this.area = area;
        this.region = region;
        this.city = city;
    }

    public String getCountryId() {
        return countryId;
    }

    public IpRegionDO setCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    public String getAreaId() {
        return areaId;
    }

    public IpRegionDO setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getRegionId() {
        return regionId;
    }

    public IpRegionDO setRegionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    public String getCityId() {
        return cityId;
    }

    public IpRegionDO setCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
