<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Collect Agent Float">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                    <security:protected-element name="collect-agent-float">
                        <strong>'${actionBean.agentFloat.agent.fullName}'
                        of ${actionBean.agentFloat.agent.vendor.name}.
                        <fmt:message key="runningtotallabel"/>: ${actionBean.agentFloat.masterFloat.total} ${agentFloat.currency}
                       </strong>
                       <s:form action="/web/collect-float" name="createForm" id="createForm" method="post">
                        <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <input type="hidden" name="agentFloat" id="agentFloat" value="${actionBean.agentFloat.id}"/>
                         <table width="100%">
                             <tr>
                                 <td align="center" colspan="3">
                                     <fmt:message key="collectfloatlabel"/> from ${actionBean.agentFloat.agent.fullName},
                                     for ${actionBean.agentFloat.formattedDayCreated}.
                                     <fmt:message key="currentamount"/>: ${actionBean.agentFloat.amountGiven} ${actionBean.agentFloat.currency}
                                 </td>
                             </tr>
                         </table>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        <fmt:message key="amountlabel"/> (${actionBean.agentFloat.currency})
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="amount" id="amount" value="${actionBean.amount}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right">
                                        <fmt:message key="submitlabel" var="submitlabel"/>
                                        <d:submit class="small" name="save" value="${submitlabel}"/>
                                    </td>
                                </tr>
                            <tbody>
                        </table>
                      </s:form>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/user/site/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
