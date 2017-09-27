<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.*, com.intiro.itr.ITRResources, com.intiro.itr.logic.superadmin.contacts.*"%>
<%
  response.setHeader("Cache-Control", "no-cache");
  response.setHeader("Pragma", "no-cache");
  response.addHeader("Cache-Control","no-store");

    String contactId = "-1";
    String companyId = "-1";
    String position = null;
    String friendLevel = null;
    String description = null;
    String firstContact = null;
    String firstName = null;
    String lastName = null;
    String knownById = "-1";
    String save = request.getParameter("btnSave");
    String delete = request.getParameter("btnDelete");
    boolean bSave = false;
    boolean bDelete = false;

    //Find out what button was pressed

    if (save != null && save.length() > 0) {
      bSave = true;
    }
    else if (delete != null && delete.length() > 0) {
      bDelete = true;
    }

      ContactEdit contactEditor = (ContactEdit) session.getAttribute(ITRResources.ITR_MODIFIED_CONTACT);

      if (bDelete) {

        boolean retVal = contactEditor.getModifiedContact().delete();

        if (retVal == false) {
          new Exception("Can not delete Contact with id = " + contactEditor.getModifiedContact().getId() + ". ");
        }
      }
      else if (bSave) {
        if (request.getParameter("contactid") != null && request.getParameter("contactid").length() > 0) {
          contactId = request.getParameter("contactid");
        }
        if (request.getParameter("company") != null && request.getParameter("company").length() > 0) {
          companyId = request.getParameter("company");
        }
        if (request.getParameter("position") != null && request.getParameter("position").length() > 0) {
          position = request.getParameter("position");
        }
        if (request.getParameter("friendlevel") != null && request.getParameter("friendlevel").length() > 0) {
          friendLevel = request.getParameter("friendlevel");
        }
        if (request.getParameter("desc") != null && request.getParameter("desc").length() > 0) {
          description = request.getParameter("desc");
        }
        if (request.getParameter("firstcontact") != null && request.getParameter("firstcontact").length() > 0) {
          firstContact = request.getParameter("firstcontact");
        }
        if (request.getParameter("firstname") != null && request.getParameter("firstname").length() > 0) {
          firstName = request.getParameter("firstname");
        }
        if (request.getParameter("lastname") != null && request.getParameter("lastname").length() > 0) {
          lastName = request.getParameter("lastname");
        }
        if (request.getParameter("knownbyuser") != null && request.getParameter("knownbyuser").length() > 0) {
          knownById = request.getParameter("knownbyuser");
        }

        contactEditor.getModifiedContact().setITR_CompanyId(Integer.parseInt(companyId));
        contactEditor.getModifiedContact().setPosition(position);
        contactEditor.getModifiedContact().setFriendLevel(friendLevel);
        contactEditor.getModifiedContact().setDescription(description);

        contactEditor.getModifiedContact().setFirstContact(new ITRCalendar(firstContact));

        contactEditor.getModifiedContact().setFirstName(firstName);
        contactEditor.getModifiedContact().setLastName(lastName);
        contactEditor.getModifiedContact().setKnownByUser_Id(Integer.parseInt(knownById));

        //Save the modified user to database.
        contactEditor.getModifiedContact().save();
      }

  response.sendRedirect("changeContactsQuery.jsp");
%>