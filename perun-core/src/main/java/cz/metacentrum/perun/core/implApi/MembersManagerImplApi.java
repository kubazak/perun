package cz.metacentrum.perun.core.implApi;

import java.util.List;

import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.Member;
import cz.metacentrum.perun.core.api.MemberGroupStatus;
import cz.metacentrum.perun.core.api.PerunSession;
import cz.metacentrum.perun.core.api.Resource;
import cz.metacentrum.perun.core.api.Status;
import cz.metacentrum.perun.core.api.User;
import cz.metacentrum.perun.core.api.UserExtSource;
import cz.metacentrum.perun.core.api.Vo;
import cz.metacentrum.perun.core.api.exceptions.AlreadyMemberException;
import cz.metacentrum.perun.core.api.exceptions.InternalErrorException;
import cz.metacentrum.perun.core.api.exceptions.MemberAlreadyRemovedException;
import cz.metacentrum.perun.core.api.exceptions.MemberNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.UserNotExistsException;
import cz.metacentrum.perun.core.api.exceptions.VoNotExistsException;

/**
 * MembersManager can find members.
 *
 * @author Michal Prochazka
 * @author Slavek Licehammer
 * @author Zora Sebestianova
 */
public interface MembersManagerImplApi {

	/**
	 *  Deletes only member data  appropriated by member id.
	 *
	 * @param perunSession
	 * @param member
	 * @throws InternalErrorException
	 * @throws MemberAlreadyRemovedException if there are 0 rows affected by removing from DB
	 */
	void deleteMember(PerunSession perunSession, Member member) throws InternalErrorException, MemberAlreadyRemovedException;

	/**
	 * Creates member entry in the database.
	 * Created member is in invalid state.
	 *
	 * @param perunSession
	 * @param vo	Vo
	 * @param user User
	 * @return created member with id filled
	 * @throws InternalErrorException
	 * @throws AlreadyMemberException
	 */
	Member createMember(PerunSession perunSession, Vo vo, User user) throws InternalErrorException, AlreadyMemberException;

	/**
	 * Get member's VO id
	 *
	 * @param perunSession
	 * @param member
	 * @return VO id
	 * @throws InternalErrorException
	 */
	public int getMemberVoId(PerunSession perunSession, Member member) throws InternalErrorException;

	/**
	 * Find member of this Vo by his login in external source
	 *
	 * @param perunSession
	 * @param vo
	 * @param userExtSource
	 * @return selected user or throws MemberNotExistsException in case the requested member doesn't exists in this Vo
	 * @throws InternalErrorException
	 * @throws MemberNotExistsException
	 */
	Member getMemberByUserExtSource(PerunSession perunSession, Vo vo, UserExtSource userExtSource) throws InternalErrorException, MemberNotExistsException;

	/**
	 * Returns member by his id.
	 *
	 * @param perunSession
	 * @param id
	 * @return member
	 * @throws InternalErrorException
	 * @throws MemberNotExistsException
	 */
	Member getMemberById(PerunSession perunSession, int id) throws InternalErrorException, MemberNotExistsException;

	/**
	 * Returns member by his user id and vo.
	 *
	 * @param perunSession
	 * @param vo
	 * @param userId
	 * @return member
	 * @throws InternalErrorException
	 * @throws MemberNotExistsException
	 */
	Member getMemberByUserId(PerunSession perunSession, Vo vo, int userId) throws InternalErrorException, MemberNotExistsException;

	/**
	 * Returns members by his user.
	 *
	 * @param perunSession
	 * @param user
	 * @return list of members
	 * @throws InternalErrorException
	 * @throws MemberNotExistsException
	 * @throws VoNotExistsException
	 * @throws UserNotExistsException
	 */
	List<Member> getMembersByUser(PerunSession perunSession, User user) throws InternalErrorException;

	/**
	 * Check if member exists in underlaying data source.
	 *
	 * @param perunSession
	 * @param member
	 * @return true if member exists in underlaying data source, false otherwise
	 *
	 * @throws InternalErrorException
	 */
	boolean memberExists(PerunSession perunSession, Member member) throws InternalErrorException;

	/**
	 * Check if member exists in underlaying data source.
	 *
	 * @param perunSession
	 * @param member
	 *
	 * @throws InternalErrorException
	 * @throws MemberNotExistsException
	 */
	void checkMemberExists(PerunSession perunSession, Member member) throws InternalErrorException, MemberNotExistsException;

	/**
	 *  Set status of the member to specified status.
	 *
	 * @param sess
	 * @param member
	 * @param status new status
	 *
	 * @throws InternalErrorException
	 */
	void setStatus(PerunSession sess, Member member, Status status) throws InternalErrorException;

	/**
	 * Convert list of users' ids into the list of members.
	 *
	 * @param sess
	 * @param usersIds
	 * @param vo
	 * @return list of members
	 * @throws InternalErrorException
	 */
	List<Member> getMembersByUsersIds(PerunSession sess, List<Integer> usersIds, Vo vo) throws InternalErrorException;

	/**
	 * Convert list of users into the list of members.
	 *
	 * @param sess
	 * @param users
	 * @param vo
	 * @return list of members
	 * @throws InternalErrorException
	 */
	List<Member> getMembersByUsers(PerunSession sess, List<User> users, Vo vo) throws InternalErrorException;

	/**
	 * Returns all members from the VO (with every status).
	 *
	 * @param sess
	 * @param vo
	 * @return number of members
	 * @throws InternalErrorException
	 */
	int getMembersCount(PerunSession sess, Vo vo) throws InternalErrorException;

	/**
	 * Returns number of Vo members with defined status.
	 *
	 * @param sess
	 * @param vo
	 * @param status
	 * @return number of members
	 * @throws InternalErrorException
	 */
	int getMembersCount(PerunSession sess, Vo vo, Status status) throws InternalErrorException;

	/**
	 * Store information about password reset request.
	 *
	 * @param sess PerunSession
	 * @param user User to reset password for
	 * @param namespace namespace to reset password in
	 * @return ID of request to be used for validation
	 * @throws InternalErrorException
	 */
	int storePasswordResetRequest(PerunSession sess, User user, String namespace) throws InternalErrorException;

	/**
	 * Creates a new member in given Vo with flag "sponsored", and linked to its sponsoring user.
	 */
	Member createSponsoredMember(PerunSession session, Vo vo, User sponsored, User sponsor) throws AlreadyMemberException, InternalErrorException;

	/**
	 * Set member to be sponsored by sponsor. Set flag and sponsorship.
	 *
	 * @param session perun session.
	 * @param sponsoredMember member who should be sponsored from now
	 * @param sponsor user which will be a sponsor for member
	 * @return sponsored member
	 * @throws InternalErrorException
	 */
	Member setSponsorshipForMember(PerunSession session, Member sponsoredMember, User sponsor) throws InternalErrorException;

	/**
	 * Unset member to not be sponsored by anybody from now. Unset flag and remove all sponsorships.
	 *
	 * @param session
	 * @param sponsoredMember member whou shouldn't be sponsored from now
	 * @return member which is not sponsored any more
	 * @throws InternalErrorException
	 */
	Member unsetSponsorshipForMember(PerunSession session, Member sponsoredMember) throws InternalErrorException;

	/**
	 * Adds another sponsoring user for a sponsored member.
	 * @param session perun session
	 * @param sponsoredMember member which is sponsored
	 * @param sponsor sponsoring user
	 * @throws InternalErrorException
	 */
	void addSponsor(PerunSession session, Member sponsoredMember, User sponsor) throws InternalErrorException;

	/**
	 * Removes a sponsoring user. In fact marks the link as inactive.
	 * @param sess perun session
	 * @param sponsoredMember member which is sponsored
	 * @param sponsor sponsoring user
	 * @throws InternalErrorException
	 */
	void removeSponsor(PerunSession sess, Member sponsoredMember, User sponsor) throws InternalErrorException;

	/**
	 * Delete all existing sponsorships for defined member. This method will delete them, not just marked.
	 *
	 * @param session perun session
	 * @param sponsoredMember member which is sponsored
	 * @throws InternalErrorException
	 */
	void deleteAllSponsors(PerunSession session, Member sponsoredMember) throws InternalErrorException;

	/**
	 * Gets members sponsored by the given user.
	 * @param sess perun session
	 * @param vo virtual organization
	 * @param sponsor sponsoring user
	 * @return list of members sponsored by the given user
	 * @throws InternalErrorException
	 */
	List<Member> getSponsoredMembers(PerunSession sess, Vo vo, User sponsor) throws InternalErrorException;

	/**
	 * Gets list of sponsored members of a VO.
	 * @param sess session
	 * @param vo virtual organization from which are the sponsored members chosen
	 * @throws InternalErrorException if given parameters are invalid
	 * @return list of members from given vo who are sponsored
	 */
	List<Member> getSponsoredMembers(PerunSession sess, Vo vo) throws InternalErrorException;

	/**
	 * Deletes all links to sponsors, even those marked as inactive.
	 * @param sess perun session
	 * @param member member which is sponsored
	 * @throws InternalErrorException
	 */
	void deleteSponsorLinks(PerunSession sess, Member member) throws InternalErrorException;

	/**
	 * Returns unified result of MemberGroupStatus for specified member and resource.
	 *
	 * If member is VALID in at least one group assigned to the resource, result is VALID.
	 * If member is not VALID in any of groups assigned to the resource, result is EXPIRED.
	 * If member is not assigned to the resource at all, result is NULL.
	 *
	 * MemberGroupStatus is never related to the members status in a VO as a whole!
	 *
	 * @param sess PerunSession
	 * @param member Member to get unified MemberGroupStatus
	 * @param resource Resource to get unified MemberGroupStatus
	 * @return MemberGroupStatus for member unified through all his groups assigned to the resource.
	 */
	MemberGroupStatus getUnifiedMemberGroupStatus(PerunSession sess, Member member, Resource resource) throws InternalErrorException;

	/**
	 * Returns unified result of MemberGroupStatus for specified user and facility.
	 *
	 * If user is VALID in at least one group assigned to at least one resource on facility, result is VALID.
	 * If user is not VALID in any of groups assigned to any of resources, result is EXPIRED.
	 * If user is not assigned to the facility at all, result is NULL.
	 *
	 * MemberGroupStatus is never related to the members status in any VO!
	 *
	 * @param sess PerunSession
	 * @param user User to get unified MemberGroupStatus
	 * @param facility Facility to get unified MemberGroupStatus
	 * @return MemberGroupStatus for user unified throught all his groups assigned to any of resources of facility.
	 */
	MemberGroupStatus getUnifiedMemberGroupStatus(PerunSession sess, User user, Facility facility) throws InternalErrorException;

}
