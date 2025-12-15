<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit ExchangeRate">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>

                    <security:protected-element name="manage-exchange-rates">
                       <s:form action="/web/edit-rate" name="createForm" id="createForm" method="post">
                        <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <input type="hidden" name="exchangeRate" id="exchangeRate" value="${actionBean.exchangeRate.id}"/>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        Day: ${actionBean.formattedDay}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="currencylabel"/>: ${actionBean.exchangeRate.currency}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="basecurrencylabel"/> : ${actionBean.exchangeRate.baseCurrency}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="ratelabel"/> (1 ${actionBean.exchangeRate.currency} to ${actionBean.exchangeRate.baseCurrency})
                                        <d:text style="background-color:#F0E68C" name="rate" value="${actionBean.rate}"/>
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
