<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Reports">

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
                    <security:protected-element name="view-report-list">
                       <s:form action="/web/report" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                            <table width="100%">
                                <tr>
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
                                                       <option value="${activityType}"/>
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
                                <thead>
                                <tr>
                                    <td align="center" colspan ="6">
                                        <fmt:message key="reporttypeslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                    <c:forEach items="${actionBean.activityTypes}" var="activityType" varStatus="loopStatus">
                                        <c:if test="${loopStatus.index % 3 == 0}">
                                            <tr class="${(loopStatus.index / 3) % 2 == 0 ? 'even' : 'odd'}">
                                        </c:if>

                                        <td>
                                            <d:link href="/web/report?activityType=${activityType}">
                                                ${activityType.label}
                                            </d:link>
                                        </td>

                                        <c:if test="${(loopStatus.index + 1) % 3 == 0 || loopStatus.last}">
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                <tbody>
                                </tbody>
                        </table>
                     </security:protected-element>
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
