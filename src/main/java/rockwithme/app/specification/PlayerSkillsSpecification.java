package rockwithme.app.specification;

import org.springframework.data.jpa.domain.Specification;
import rockwithme.app.model.entity.Band;
import rockwithme.app.model.entity.Goal;
import rockwithme.app.model.entity.PlayerSkills;
import rockwithme.app.model.entity.Style;

import javax.persistence.criteria.*;

public class PlayerSkillsSpecification extends BaseSpecification implements Specification<PlayerSkills> {

    public PlayerSkillsSpecification() {
    }
    public PlayerSkillsSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<PlayerSkills> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (this.getCriteria().getOperation().equalsIgnoreCase(":")) {
            return criteriaBuilder.equal(root.get(this.getCriteria().getKey()), this.getCriteria().getValue());
        }
        return null;
    }
}
