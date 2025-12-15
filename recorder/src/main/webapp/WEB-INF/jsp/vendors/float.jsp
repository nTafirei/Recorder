<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Agent Float History">

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
                    <security:protected-element name="view-agent-float-history">
                       <s:form action="/web/agent-float-history" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                         <input type="hidden" name="agent" id="agent" value="${actionBean.agent.id}"/>
                         <input type="hidden" name="currPage" id="currPage" value="${actionBean.page.currPage}"/>
                                <c:if test="${empty actionBean.floats}">
                                    <strong><fmt:message key="nofloatsfound"/> ${actionBean.agent.fullName}
                                     <fmt:message key="vendor"/> : ${actionBean.agent.vendor.name}
                                     </strong>
                                </c:if>

                                <c:if test="${!empty actionBean.floats}">
                                           <strong>
                                                  <fmt:message key="showinglabel"/>
                                                  ${actionBean.page.numItemsShowing} of ${actionBean.page.totalItemsFound},
                                                  page is ${actionBean.page.currPage}.  ${actionBean.page.totalItemsFound}
                                                  <fmt:message key="floatsfound"/> '${actionBean.agent.fullName}'
                                                  of ${actionBean.agent.vendor.name}.
                                                  <fmt:message key="runningtotallabel"/>: ${actionBean.masterFloat.total} ${agentFloat.currency}
                                                <br/>
                                                <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                          varStatus="loopStatus">
                                                  <d:link href="/web/agent-float-history?agent=${actionBean.agent.id}&currPage=${pageItem}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&_eventName=search">${pageItem}</d:link> |
                                                </c:forEach>
                                            </strong>
                                </c:if>
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
                                    <td align="center" colspan ="9">
                                        <fmt:message key="floatdetailslabel"/>
                                        ${actionBean.agent.fullName} (${actionBean.start} to ${actionBean.end})
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="date"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountgivenlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="givenbylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountsoldlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountcollectedlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="collectedlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="collectedbylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="runningtotallabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                <tr>

                                </thead>
                                <tbody>

                                            <c:forEach items="${actionBean.floats}" var="agentFloat" varStatus="loopStatus">
                                            <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                                  <td>
                                                      ${agentFloat.formattedDayCreated}
                                                  </td>
                                                  <td>
                                                        <c:if test="${!empty agentFloat.amountGiven}">
                                                            ${agentFloat.amountGiven} ${agentFloat.currency}
                                                        </c:if>
                                                  </td>
                                                  <td>
                                                        <c:if test="${!empty agentFloat.amountGiven}">
                                                            ${agentFloat.givenBy.fullName}
                                                        </c:if>
                                                  </td>
                                                  <td>
                                                      ${agentFloat.amountSold} ${agentFloat.currency}
                                                  </td>
                                                  <td>
                                                      ${agentFloat.amountCollected} ${agentFloat.currency}
                                                  </td>
                                                  <td>
                                                      ${agentFloat.collected.label}
                                                  </td>
                                                  <td>
                                                     <c:if test="${!empty agentFloat.collectedBy}">
                                                        ${agentFloat.collectedBy.fullName}
                                                      </c:if>
                                                  </td>
                                                  <td>

                                                      <c:if test="${agentFloat.isToday}">
                                                        ${agentFloat.masterFloat.total} ${agentFloat.currency}
                                                       </c:if>
                                                  </td>
                                                  <td>

                                                     <c:if test="${agentFloat.canBeCollected}">
                                                        <security:protected-element name="collect-agent-float">
                                                            <d:link href="/web/collect-float?agent=${actionBean.agent.id}&agentFloat=${agentFloat.id}">
                                                            collect from agent</d:link>
                                                            <c:if test="${agentFloat.canBeGiven}">
                                                                |
                                                            </c:if>
                                                        </security:protected-element>
                                                    </c:if>
                                                    <c:if test="${agentFloat.canBeGiven}">
                                                        <security:protected-element name="give-agent-float">
                                                            <d:link href="/web/top-up-float?agent=${actionBean.agent.id}&agentFloat=${agentFloat.id}">
                                                            top up agent</d:link>
                                                        </security:protected-element>
                                                    </c:if>
                                                  </td>
                                                </tr>
                                            </c:forEach>
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
                                          maxDate: "+30d", // Allow dates up to 30 days from now
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
                                          maxDate: "+30d", // Allow dates up to 30 days from now
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
