<#import "parts/common.ftl" as com>

<@com.page>
    <div>Polivalka</div>
    <table>
        <#list flowerList as flower>
            <tr>
                <td>${flower.getName()}</td>
                <td>${flower.getCurrentMoistureLevel()}</td>
                <td><a href="/flowers/${flower.getId()}">Edit</a></td>
            </tr>
        </#list>
    </table>
</@com.page>