package rockwithme.app.repository.specification;

public class BaseSpecification {
    private SearchCriteria criteria;

    public BaseSpecification() {
    }

    public BaseSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
