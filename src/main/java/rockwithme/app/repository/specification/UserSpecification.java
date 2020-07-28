package rockwithme.app.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import rockwithme.app.model.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification extends BaseSpecification implements Specification<User> {

    public UserSpecification() {
    }

    public UserSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (this.getCriteria().getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(this.getCriteria().getKey()), this.getCriteria().getValue().toString());
        }
        else if (this.getCriteria().getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(this.getCriteria().getKey()), this.getCriteria().getValue().toString());
        }
        else if (this.getCriteria().getOperation().equalsIgnoreCase(":")) {
//            if (root.get(this.getCriteria().getKey()).getJavaType() == String.class) {
            if (this.getCriteria().getKey().equals("username") || this.getCriteria().getKey().equals("firstName") || this.getCriteria().getKey().equals("lastName")) {
                return builder.like(
                        root.get(this.getCriteria().getKey()), "%" + this.getCriteria().getValue() + "%");
            } else {
                return builder.equal(root.get(this.getCriteria().getKey()), this.getCriteria().getValue());
            }
        }

        return null;
    }

}
