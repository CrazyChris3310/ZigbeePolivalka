<#import "common.ftl" as com>

<@com.page>
    <h2>POLIVALKA</h2>
    <table>
        <#list flowerList as flower>
            <tr>
                <td class="flowers-names" style="width: 70%">${flower.getName()}</td>
                <td class="flowers-characteristics" style="width: 7%">${flower.getCurrentMoistureLevel()}%</td>
                <td class="flowers-characteristics" style="width: 15%"><a href="/${flower.getId()}">EDIT</a></td>
                <td class="flowers-characteristics" style="width: 7%"><a href="/delete/${flower.getId()}">X</a></td>
            </tr>
        </#list>
    </table>
    <a href="/search">ADD NEW FLOWERS</a>
</@com.page>