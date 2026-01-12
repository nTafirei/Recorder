<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="${actionBean.pageTitle}">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper" style="background-color: #fdfdfd; border-radius: 10px 10px 10px 10px;overflow:hidden;">
            <div class="container">
                <div id="content">
                    <s:errors/>
                    <c:if test="${empty actionBean.recordings}">
                        <fmt:message key="norecordingsfound"/> for ${actionBean.searchDates}
                    </c:if>
                        <s:form action="${actionBean.listPage}" name="createForm" id="createForm" method="post">
                            <input type="hidden" name="user" id="user" value="${actionBean.user.id}"/>
                            <table width="100%">
                                <tr>
                                    <td align="center" colspan = "3">
                                        <c:if test="${!empty actionBean.recordings}">
                                            <strong>
                                            <fmt:message key="showinglabel"/>
                                            ${actionBean.page.numItemsShowing} of
                                            ${actionBean.page.totalItemsFound}.
                                            ${actionBean.page.totalItemsFound} <fmt:message key="recordings"/> for ${actionBean.user.fullName} for ${actionBean.searchDates},
                                            page is ${actionBean.page.currPage}.
                                                    <br/>
                                                    <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                              varStatus="loopStatus">
                                                      <d:link href="${actionBean.listPage}?currPage=${pageItem}&user=${actionBean.user.id}">${pageItem}</d:link> |
                                                    </c:forEach>
                                        </c:if>
                                    </td>
                                        <td>
                                            <fmt:message key="startdatelabel"/> (dd-mm-yyyy)
                                            <d:text style="background-color:#F0E68C" name="startDate" id="startDate" class="startDate"/>
                                        </td>
                                        <td>
                                            <fmt:message key="enddatelabel"/> (dd-mm-yyyy)
                                            <d:text style="background-color:#F0E68C" name="endDate" id="endDate" class="endDate"/>
                                        </td>
                                        <td align="right" valign="bottom">
                                            <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                            <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                        </td>
                                </tr>
                            </table>
                        </s:form>
                    <table class="alternating">
                        <c:if test="${!empty actionBean.recordings}">
                            <thead>
                            <tr>
                                <th align="left">
                                    <fmt:message key="namelabel"/>
                                </th>
                                <th align="left">
                                    <fmt:message key="locationlabel"/>
                                </th>
                                <th align="left">
                                    <fmt:message key="datecreatedlabel"/>
                                </th>
                                <th align="left">
                                    <fmt:message key="actionlabel"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${actionBean.recordings}" var="recording"
                                       varStatus="loopStatus">
                                <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                    <td>
                                            ${recording.name}
                                    </td>
                                    <td>
                                            ${recording.deviceLocation}
                                    </td>
                                    <td>
                                            ${recording.formattedDateCreated}
                                    </td>
                                    <td>
                                                 <d:link
                                                             href="/web/download?attachment=${recording.attachment.id}" target="_blank">
                                                             <fmt:message key="listenlabel"/>
                                                 </d:link>
                                                 | <d:link
                                                             href="/web/recordings?recording=${recording.id}&_eventName=delete">
                                                             <fmt:message key="deletelabel"/>
                                                 </d:link>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </c:if>
                    </table>
                </div>
            </div>
                            <script language="javascript">
                                $(document).ready(function() {
                                      const startDate = $("#startDate");
                                      if (startDate) {
                                        startDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }
                                     const endDate = $("#endDate");
                                      if (endDate) {
                                        endDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }
                                });
                            </script>
        </div>
    </s:layout-component>
</s:layout-render>

