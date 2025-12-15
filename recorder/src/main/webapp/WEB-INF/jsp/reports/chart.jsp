<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>
<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Sales Chart">

    <s:layout-component name="head">
        <script src="${actionBean.scriptPath}/chartjs/chart.umd.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">
        <div id="main-wrapper main-contents">

            <div class="container">
                <div id="content">
                    <div style="width: 800px;">

                        <table width="100%">
                                    <c:if test="${actionBean.distCount > 0}">
                                        <tr>
                                        <td align="right">

                                                <d:link href="/web/distributions-report/search?&province=${actionBean.province.id}&district=${actionBean.district.id}&ward=${actionBean.ward.id}&batch=${actionBean.batch.id}&shouldGenerate=Y&doDetails=Y"><fmt:message key="detailsmenulabel"/></d:link>
                                        </td>
                                        <td align="right">
                                            <c:if test="${empty actionBean.district || empty actionBean.ward || empty actionBean.batch}">
                                                 <d:link href="/web/distributions-report/search?&province=${actionBean.province.id}&district=${actionBean.district.id}&ward=${actionBean.ward.id}&batch=${actionBean.batch.id}&shouldGenerate=N&doDetails=N"><fmt:message key="refinemenulabel"/></d:link>
                                            </c:if>
                                        </td>
                                     <tr>
                                        <td colspan="2">
                                             <canvas id="distributions"></canvas>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${actionBean.distCount == 0}">
                                    No distributions found for the specified parameters
                                </c:if>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

        <c:if test="${actionBean.distCount > 0}">
                    <script language="javascript">
                        var data = [
                        ${actionBean.agentData}
                      ];

                       new Chart(
                        document.getElementById('distributions'),
                        {
                          type: 'bar',
                          data: {
                            labels: data.map(row => row.agent),
                            datasets: [
                              {
                                label: '${actionBean.headerLabel}',
                                data: data.map(row => row.distCount)
                              }
                            ]
                          }
                        }
                      );
                      </script>
        </c:if>