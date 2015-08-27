package cn.afterturn.http.entity.params;

public class ParamsEntity implements Comparable<ParamsEntity> {

    private String  name  = "";

    private String  value;

    private int     order = 0;

    private boolean isSign;

    public ParamsEntity() {

    }

    public ParamsEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public String getValue() {
        return value;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setSign(boolean isSign) {
        this.isSign = isSign;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(ParamsEntity next) {
        return this.getOrder() - next.getOrder();
    }

}
