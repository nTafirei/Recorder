<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Activity Report">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                        <c:if test="${empty actionBean.activities}">
                            <strong><fmt:message key="noactivitiesfound"/> for '${actionBean.activityType.label}'</strong>
                        </c:if>
                       <s:form action="/web/report" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                            <table width="100%">
                                <tr>
                                    <td>
                                        <c:if test="${!empty actionBean.activities}">
                                            <strong>
                                                  <fmt:message key="showinglabel"/>
                                                  ${actionBean.page.numItemsShowing} of ${actionBean.page.totalItemsFound},
                                                  page is ${actionBean.page.currPage}
                                                <br/>
                                                <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                          varStatus="loopStatus">
                                                  <d:link href="/web/report/${actionBean.user.id}?currPage=${pageItem}&activityType=${actionBean.activityType}">${pageItem}</d:link> |
                                                </c:forEach>
                                            </strong>
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
                                    <td>
                                            <fmt:message key="typelabel"/>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="activityType" id="activityType" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectactivitytypelabel"/></s:option>
                                                   <c:forEach items="${actionBean.activityTypes}" var="activityType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.activityType !=null &&
                                                             actionBean.activityType == activityType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${activityType}" ${selectedValue}/>
                                                      ${activityType.label}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                    <td align="right" valign="bottom">
                                        <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                        <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                    </td>
                                </tr>
                            </table>
                         </s:form>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.activities}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actorlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="typelabel"/>
                                    </td>
                                     <c:if test="${actionBean.activityType.actuallyHasAmount}">
                                        <td>
                                            <fmt:message key="amountlabel"/>
                                        </td>
                                    </c:if>
                                    <td>
                                        <fmt:message key="thedetailslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.activities}" var="activity"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${activity.formattedDateCreated}
                                        </td>
                                        <td>
                                            ${activity.actor.fullName}
                                        </td>
                                        <td>
                                            ${activity.title}
                                        </td>
                                        <td>
                                            ${activity.activityType.label}
                                        </td>
                                        <c:if test="${activity.activityType.actuallyHasAmount}">
                                            <td>
                                                ${activity.amount}
                                            </td>
                                        </c:if>
                                        <td>
                                                <d:link href="/web/activity-details?activity=${activity.id}">
                                                    <fmt:message key="detailslabel"/>
                                                </d:link>
                                        </td>
                                    </tr>
                                </c:forEach>
                                    <tr>
                                        <td colspan="7" align="right">
                                                    <d:link target="_blank" href="/web/report?exportType=PDF&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&activityType=${actionBean.activityType}">
                                                        <fmt:message key="exportropdflabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/report?exportType=EXCEL&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&activityType=${actionBean.activityType}">
                                                        <fmt:message key="exporttoexcellabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/report?exportType=CSV&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&activityType=${actionBean.activityType}">
                                                        <fmt:message key="exporttotextlabel"/>
                                                    </d:link>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:if>
                        </table>
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
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
