package cz.metacentrum.perun.core.impl.modules.attributes;


import cz.metacentrum.perun.core.api.*;
import cz.metacentrum.perun.core.api.exceptions.InternalErrorException;
import cz.metacentrum.perun.core.impl.PerunSessionImpl;
import cz.metacentrum.perun.core.implApi.modules.attributes.UserVirtualAttributesModuleAbstract;
import cz.metacentrum.perun.core.implApi.modules.attributes.UserVirtualAttributesModuleImplApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains all status of the user in Vo
 *
 * @author Jakub Zak <jjakub.zak@gmail.com>
 */
public class urn_perun_user_attribute_def_virt_statusesInVos extends UserVirtualAttributesModuleAbstract implements UserVirtualAttributesModuleImplApi {

    @Override
    public Attribute getAttributeValue(PerunSessionImpl sess, User user, AttributeDefinition attributeDefinition) throws InternalErrorException {

        Attribute attribute = new Attribute(attributeDefinition);

        List<Member> members = sess.getPerunBl().getMembersManagerBl().getMembersByUser(sess, user);
        Map<String, Status> result = new HashMap<>();
        for (Member member : members) {
            result.put(sess.getPerunBl().getMembersManagerBl().getMemberVo(sess, member).getShortName(), member.getStatus());
        }
        attribute.setValue(result);
        return attribute;
    }

    @Override
    public AttributeDefinition getAttributeDefinition() {
        AttributeDefinition attr = new AttributeDefinition();
        attr.setNamespace(AttributesManager.NS_USER_ATTR_VIRT);
        attr.setFriendlyName("statusInVos");
        attr.setDisplayName("Status member In Vos");
        attr.setType(Map.class.getName());
        attr.setDescription("Status member in Vos.");
        return attr;
    }

}
