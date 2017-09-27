<%@ page session="true"%>
<%@ page import="com.intiro.itr.util.personalization.*, com.intiro.itr.common.*, com.intiro.itr.ITRResources, com.intiro.itr.logic.weekreport.WeekReport"%>
<%@ page import="com.intiro.itr.logic.weekreport.*"%>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.addHeader("Cache-Control","no-store");

      UserProfile userProfile = (UserProfile) session.getAttribute(ITRResources.ITR_USER_PROFILE);

      
      
      
String lnk ="";     //Används när vi bygger länkar
 %>
<!--
   (Please keep all copyright notices.)
   This frameset document includes the FolderTree script.
   Script found at: http://www.treeview.net
   Author: Marcelino Alves Martins

   Instructions:
   - Do not make any changes to this file outside the style tag.
   - Through the style tag you can change the colors and types
     of fonts to the particular needs of your site. 
   - A predefined block has been made for stylish people with
     black backgrounds.
-->
<html>
<head>
   <link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS">
   <style>
   	BODY {
   		color: #000066;
   		background-color: #F8F8F0;
   	}
   	TD {
   		text-decoration: none;
   		white-space:nowrap;
   	}

   	A:link {
   		font-size: 8pt;
   		color: #000066;
   		text-decoration: none;
   	}
   	A:visited {
   		font-size: 8pt;
   		color: #000066;
   		text-decoration: none;
   	}
   	A:hover {
   		color: red
   	}
   </style>
</head>

<!-- NO CHANGES PAST THIS LINE -->

<!-- Code for browser detection -->
<script type="text/javascript" src="helpers/ua.js"></script>

<!-- Infrastructure code for the tree -->
<script type="text/javascript" src="helpers/ftiens4.js"></script>
<!-- Execution of the code that actually builds the specific tree.
     The variable foldersTree creates its structure with calls to
     gFld, insFld, and insDoc -->
<script>
// You can find instructions for this file here:
// http://www.treeview.net

// Decide if the names are links or just the icons
USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks

// Decide if the tree is to start all open or just showing the root folders
STARTALLOPEN = 0 //replace 0 with 1 to show the whole tree

USEICONS = 0   // 1 to use folder and link icons in the menu. 0 to have no icons.
// Highlights the lasts clicked link in the menu.
HIGHLIGHT = 1
ICONPATH = 'images/MenuIcons/'
HIGHLIGHT_COLOR = 'white';
HIGHLIGHT_BG    = '#333366'; //'blue';

/*
   Possible values for first argument in gLnk
	R	page target is default frame (right frame)
	T	page target is _top = whole window
	B	page target is _blank = new window
	S	page target is _self = this window
	Rh The http protocol is appended by the script
*/

// Root folder
foldersTree = gFld("<b>Home Page</b>", "frameset.jsp")

   //Time reporting
   aux1 = insFld(foldersTree, gFld("Time Report", ""))
   aux2 = insFld(aux1, gFld("Submitted", ""))
   
   <%
    //Weeks weeks = new Weeks( userProfile, "submitted" ); 
    
    Common common = new Common(userProfile);
    
    String[] submitYears = common.getSubmitedYears(true);
    
   
   lnk = "";
   for (int i=0; i < submitYears.length;i++) {
       lnk = "insDoc(aux2, gLnk(\"R\", \" " + submitYears[i].substring(0, 4) + "  \", \"viewWeeks.jsp?mode=submitted&year=" + submitYears[i].substring(0, 4) + "\"))";
   %>
     <%= lnk %>  
   <% }
%>
      insDoc(aux1, gLnk("R", "Todo", "viewWeeks.jsp?mode=todo"))
      insDoc(aux1, gLnk("R", "Future", "viewWeeks.jsp?mode=future"))

   // Settings
   aux1 = insFld(foldersTree, gFld("Settings", ""))
      insDoc(aux1, gLnk("R", "Change Password", "changePassword.jsp"))

	<%
	if(userProfile.getRole().isAdmin() || userProfile.getRole().isSuperAdmin()) {
            //Ladda bara om det är admin - tar värdefull tid.
            String[] approvedYears = common.getSubmitedYears(false);
	%>
   // Administration
   aux1 = insFld(foldersTree, gFld("Admin", ""))
      insDoc(aux1, gLnk("R", "Approve Weeks", "admin.jsp"))
      
      aux2 = insFld(aux1, gFld("Approved Weeks", ""))
   
   <% lnk = "";
        for (int i=0; i < approvedYears.length;i++) {
            lnk = "insDoc(aux2, gLnk(\"R\", \" " + approvedYears[i].substring(0, 4) + "  \", \"weeksApproved.jsp?year=" + approvedYears[i].substring(0, 4) + "&userId=\"))";
            out.println(lnk);
        }
    %>

      insDoc(aux1, gLnk("R", "Late Weeks", "weeksNeedingSubmit.jsp"))
	<%
	}
	if(userProfile.getRole().isSuperAdmin()) {
	%>
	
   // Super admin
   aux1 = insFld(foldersTree, gFld("Super admin", ""))
      insDoc(aux1, gLnk("R", "Activity", "changeActivityQuery.jsp"))
      insDoc(aux1, gLnk("R", "Companies", "changeCompaniesQuery.jsp"))
      insDoc(aux1, gLnk("R", "Contacts", "changeContactsQuery.jsp"))
      insDoc(aux1, gLnk("R", "Project memb.", "changeProjectMembersQuery.jsp"))
      insDoc(aux1, gLnk("R", "Project", "changeProjectsQuery.jsp"))
      insDoc(aux1, gLnk("R", "Project - Activity", "changeProjectActivitiesQuery.jsp"))
      insDoc(aux1, gLnk("R", "Users", "changeUsersQuery.jsp"))
      insDoc(aux1, gLnk("R", "Users - Emails", "changeEmailsQuery.jsp"))
      insDoc(aux1, gLnk("R", "Users - Phones", "changePhonesQuery.jsp"))
    <%
	}
	%>
	  
   //Report
   insDoc(foldersTree, gLnk("R", "Reports", "listReports.jsp"));

   //Contact
   insDoc(foldersTree, gLnk("R", "<b>Contact</b>", "mailto:info@intiro.se"));
   
   //Logout
   insDoc(foldersTree, gLnk("T", "<b>Logout</b>", "logout.jsp"));
</script>

<body topmargin=16 marginheight=16 leftmargin="17" background="images/skins/hiq/menu_back.gif">

<!-- By making any changes to this code you are violating your user agreement.
     Corporate users or any others that want to remove the link should check 
     the online FAQ for instructions on how to obtain a version without the link -->
<!-- Removing this link will make the script stop from working -->
<div style="position:absolute; top:0; left:0; ">
   <table border=0>
      <tr>
         <td>
            <font size=-2>
               <a style="font-size:7pt;text-decoration:none;color:silver"
                  href=http://www.treeview.net/treemenu/userhelp.asp target=_top>
                     <!--Tree Menu Help-->
               </a>
            </font>
         </td>
      <tr>
   </table>
<!-- Build the browser's objects and display default view of the tree. -->
<script>initializeDocument()</script>

</body>
</html>
