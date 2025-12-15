<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Users">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <security:protected-element name="show-user-list">
                        <c:if test="${empty actionBean.users}">
                            <fmt:message key="nousersfound">
                             <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                             </fmt:message>
                        </c:if>
                        <table width="100%">
                            <tr>
                                    <td>
                                        <c:if test="${!empty actionBean.users}">
                                            <strong>
                                                  <fmt:message key="showinglabel"/>
                                                  ${actionBean.page.numItemsShowing} of ${actionBean.page.totalItemsFound},
                                                  page is ${actionBean.page.currPage}
                                                <br/>
                                                <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                          varStatus="loopStatus">
                                                  <d:link href="/web/users/${actionBean.user.id}?currPage=${pageItem}&activeStatus=${actionBean.activeStatus}">${pageItem}</d:link> |
                                                </c:forEach>
                                            </strong>
                                        </c:if>
                                    </td>
                                <td align="center" colspan="3">
                                    <d:link href="/web/search-users"><fmt:message key="searchusersmenulabel"/></d:link>
                                    |
                                    <d:link href="/web/users?activeStatus=ACTIVE"><fmt:message key="activelabel"/></d:link>
                                    |
                                    <d:link href="/web/users?activeStatus=NOT_ACTIVE"><fmt:message key="notactivelabel"/></d:link>
                                </td>
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
                                         <fmt:message key="id"/>
                                     </td>
                                     <td>
                                         <fmt:message key="statuslabel"/>
                                     </td>
                                     <td>
                                         <fmt:message key="balance"/>
                                     </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.users}" var="user"
                                           varStatus="loopStatus">
                                <c:if test="${user.isAdmin == false}">
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
                                             ${user.nationalId}
                                        </td>
                                        <td nowrap="nowrap">
                                             ${user.activeStatus.status}
                                        </td>
                                        <td nowrap="nowrap">
                                             ${user.account.availableBalance.amount} ${user.account.currency}
                                        </td>
                                         <td align="right">
                                                 <d:link
                                                             href="/web/user-details/${user.id}">
                                                             <fmt:message key="detaillabel"/>
                                                   </d:link>
                                                 <security:protected-element name="view-user-transactions">
                                                     | <d:link
                                                                 href="/web/user-transaction-history/${user.id}">
                                                                 <fmt:message key="transactionhistorylabel"/>
                                                       </d:link>
                                                 </security:protected-element>
                                                 <security:protected-element name="top-up-users">
                                                     | <d:link
                                                                 href="/web/top-up?user=${user.id}">
                                                                 <fmt:message key="topuplabel"/>
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
                                                <c:if test="${actionBean.user.verified == 'NO'}">
                                                     <security:protected-element name="verify-users">
                                                           |
                                                           <d:link
                                                           href="/web/verify-user?user=${actionBean.user.id}&_eventName=verify">
                                                           <fmt:message key="verifyuserlabel"/>
                                                           </d:link>
                                                     </security:protected-element>
                                                </c:if>
                                               <c:if test="${user.verified == 'NO'}">
                                                     <security:protected-element name="verify-users">
                                                           |
                                                           <d:link
                                                           href="/web/verify-user/${user.id}/verify">
                                                           <fmt:message key="verifyuserlabel"/>
                                                           </d:link>
                                                     </security:protected-element>
                                                </c:if>
                                        </td>
                                    </tr>
                                    </c:if>
                                </c:forEach>
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
