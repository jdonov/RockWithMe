package rockwithme.app.specification;

import org.springframework.data.jpa.domain.Specification;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Goal;
import rockwithme.app.model.entity.Style;

import javax.persistence.criteria.*;

public class BandSpecification extends BaseSpecification implements Specification<Band> {

    public BandSpecification() {
    }

    public BandSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Band> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (this.getCriteria().getOperation().equalsIgnoreCase(":")) {
//            if (root.get(criteria.getKey()).getJavaType() == String.class) {
            if (this.getCriteria().getKey().equals("name")) {
                return criteriaBuilder.like(
                        root.get(this.getCriteria().getKey()), "%" + this.getCriteria().getValue() + "%");
            } else if (this.getCriteria().getKey().equals("styles")) {
                SetJoin<Band, Style> styles = root.joinSet("styles");
                return styles.in(this.getCriteria().getValue());
            } else if (this.getCriteria().getKey().equals("goals")) {
                SetJoin<Band, Goal> goals = root.joinSet("goals");
                return goals.in(this.getCriteria().getValue());
            } else {
                return criteriaBuilder.equal(root.get(this.getCriteria().getKey()), this.getCriteria().getValue());
            }
        }
        return null;
    }
}
