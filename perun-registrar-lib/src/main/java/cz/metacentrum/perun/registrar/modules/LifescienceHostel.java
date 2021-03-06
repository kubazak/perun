package cz.metacentrum.perun.registrar.modules;

import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.AttributesManager;
import cz.metacentrum.perun.core.api.ExtSource;
import cz.metacentrum.perun.core.api.PerunSession;
import cz.metacentrum.perun.core.api.User;
import cz.metacentrum.perun.core.api.UserExtSource;
import cz.metacentrum.perun.core.api.exceptions.PerunException;
import cz.metacentrum.perun.core.api.exceptions.UserExtSourceExistsException;
import cz.metacentrum.perun.core.bl.PerunBl;
import cz.metacentrum.perun.registrar.RegistrarManager;
import cz.metacentrum.perun.registrar.RegistrarModule;
import cz.metacentrum.perun.registrar.model.Application;
import cz.metacentrum.perun.registrar.model.ApplicationFormItemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Module for VOs managing LifescienceHostel
 *
 * @author Pavel Zlamal <256627@mail.muni.cz>
 */
public class LifescienceHostel implements RegistrarModule {

	final static Logger log = LoggerFactory.getLogger(LifescienceHostel.class);

	private RegistrarManager registrar;

	@Override
	public void setRegistrar(RegistrarManager registrar) {
		this.registrar = registrar;
	}

	@Override
	public List<ApplicationFormItemData> createApplication(PerunSession session, Application application, List<ApplicationFormItemData> data) throws PerunException {
		return data;
	}

	/**
	 * Create proper UserExtSource
	 */
	@Override
	public Application approveApplication(PerunSession session, Application app) throws PerunException {

		PerunBl perun = (PerunBl)session.getPerun();

		User user = app.getUser();

		if (user == null) {

			log.error("At the end of approval action, we should have user present in application: {}", app);

		} else {

			Attribute userLogin = perun.getAttributesManagerBl().getAttribute(session, user, AttributesManager.NS_USER_ATTR_DEF + ":login-namespace:lifescience-hostel");
			if (userLogin != null && userLogin.getValue() != null) {
				ExtSource extSource = perun.getExtSourcesManagerBl().getExtSourceByName(session, "https://login.bbmri-eric.eu/lshostel/");
				UserExtSource ues = new UserExtSource(extSource, userLogin.valueAsString() + "@lifescience-hostel.org");
				ues.setLoa(0);

				try {
					perun.getUsersManagerBl().addUserExtSource(session, user, ues);
				} catch (UserExtSourceExistsException ex) {
					// this is OK
				}

			}

			// User doesn't have login - don't set UES

		}

		return app;

	}

	@Override
	public Application rejectApplication(PerunSession session, Application app, String reason) throws PerunException {
		return app;
	}

	@Override
	public Application beforeApprove(PerunSession session, Application app) throws PerunException {
		return app;
	}

	@Override
	public void canBeApproved(PerunSession session, Application app) throws PerunException {
	}

	@Override
	public void canBeSubmitted(PerunSession session, Map<String, String> params) throws PerunException {
	}

}
