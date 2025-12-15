<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Vendor Agents">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <security:protected-element name="view-vendor-agents">
                        <c:if test="${empty actionBean.users}">
                            <fmt:message key="noagentsfound">
                              <fmt:param>${actionBean.agentStatus.status}</fmt:param>
                            </fmt:message>
                             for ${actionBean.vendor.name}</strong>
                        </c:if>
                        <table width="100%">
                            <tr>
                                <td align="center" colspan = "3">
                                    <c:if test="${!empty actionBean.users}">
                                        <strong>${actionBean.usersSize}
                                        <fmt:message key="agentsfound">
                                          <fmt:param>${actionBean.agentStatus.status}</fmt:param>
                                         </fmt:message>
                                        for ${actionBean.vendor.name}</strong>
                                    </c:if>
                                </td>
                                <c:if test="${actionBean.isLoggedIn == true}">
                                    <security:protected-element name="manage-vendors">
                                        <td align="center" colspan="3">
                                            <d:link href="/web/search-users">
                                                <fmt:message key="searchusersmenulabel"/>
                                            </d:link>
                                        | <d:link
                                              href="/web/vendor-agents?agentStatus=ACTIVE&vendor=${actionBean.vendor.id}">
                                                <fmt:message key="activelabel"/>
                                              </d:link>
                                          |
                                            <d:link
                                              href="/web/vendor-agents?agentStatus=NOT_ACTIVE&vendor=${actionBean.vendor.id}">
                                                <fmt:message key="notactivelabel"/>
                                            </d:link>
                                        </td>
                                    </security:protected-element>
                                </c:if>
                            </tr>
                        </table>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.users}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="emaillabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="mobilephonelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                     <td>
                                         <fmt:message key="id"/>
                                     </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.users}" var="user"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                                ${user.firstName} ${user.lastName}
                                        </td>
                                        <td>
                                                <a href="mailto:${user.email}">${user.email}</a>
                                        </td>
                                        <td nowrap="nowrap">
                                                ${user.mobileNumber}
                                        </td>
                                        <td nowrap="nowrap">
                                                ${user.agentStatus.status}
                                        </td>
                                        <td nowrap="nowrap">
                                             ${user.nationalId}
                                        </td>
                                         <td align="right">
                                                 <d:link
                                                             href="/web/user-details/${user.id}">
                                                             <fmt:message key="detaillabel"/>
                                                   </d:link>
                                                   <security:protected-element name="view-vendor-transactions">
                                                     | <d:link
                                                                 href="/web/agent-transaction-history/${user.id}">
                                                                 <fmt:message key="transactionhistorylabel"/>
                                                       </d:link>
                                                   </security:protected-element>
                                                   <security:protected-element name="view-agent-float-history">
                                                     | <d:link
                                                                 href="/web/agent-float-history?agent=${user.id}">
                                                                 <fmt:message key="floathistorylabel"/>
                                                       </d:link>
                                                   </security:protected-element>
                                                 <security:protected-element name="edit-users">
                                                     | <d:link
                                                                 href="/web/edit-user?user=${user.id}">
                                                                 <fmt:message key="editlabel"/>
                                                       </d:link>
                                                     <c:if test="${user.activeStatus == 'ACTIVE'}">
                                                        | <d:link
                                                                 href="/web/edit-user?user=${user.id}&_eventName=disable">
                                                                 <fmt:message key="disablelabel"/>
                                                        </d:link>
                                                     </c:if>
                                                     <c:if test="${user.activeStatus == 'NOT_ACTIVE'}">
                                                        | <d:link
                                                                 href="/web/edit-user?user=${user.id}&_eventName=enable">
                                                                 <fmt:message key="enablelabel"/>
                                                        </d:link>
                                                     </c:if>
                                                </security:protected-element>
                                                <security:protected-element name="manage-vendor-agents">
                                                    <c:if test="${user.agentStatus == 'ACTIVE'}">
                                                                  | <d:link
                                                                              href="/web/manage-agent-role?user=${user.id}&_eventName=demote">
                                                                              <fmt:message key="deactivateagentlabel"/>
                                                                    </d:link>
                                                      </c:if>
                                                    <c:if test="${user.agentStatus == 'NOT_ACTIVE'}">
                                                                  | <d:link
                                                 href="/web/manage-agent-role?user=${user.id}&_eventName=promote&vendor=${user.vendor.id}">
                                                                              <fmt:message key="activateagentlabel"/>
                                                                    </d:link>
                                                    </c:if>
                                                 </security:protected-element>
                                               <c:if test="${user.verified == 'NO'}">
                                                     <security:protected-element name="verify-users">
                                                           |
                                                           <d:link
                                                           href="/web/verify-user?user=${user.id}&_eventName=verify">
                                                           <fmt:message key="verifyuserlabel"/>
                                                           </d:link>
                                                     </security:protected-element>
                                                </c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="7" align="right">
                                                    <d:link target="_blank" href="/web/vendor-agents?agentStatus=${actionBean.agentStatus}&exportType=PDF&_eventName=export&vendor=${actionBean.vendor.id}">
                                                        <fmt:message key="exportropdflabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/vendor-agents?agentStatus=${actionBean.agentStatus}&exportType=EXCEL&_eventName=export&vendor=${actionBean.vendor.id}">
                                                        <fmt:message key="exporttoexcellabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/vendor-agents?agentStatus=${actionBean.agentStatus}&exportType=CSV&_eventName=export&vendor=${actionBean.vendor.id}">
                                                        <fmt:message key="exporttotextlabel"/>
                                                    </d:link>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/security/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
