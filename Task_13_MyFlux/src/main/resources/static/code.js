window.addEventListener("load", (event) => {

    var body = document.getElementsByTagName("body");
    console.log(body)

    console.log("page is fully loaded");

    fetch('/api').then(async function (response) {

        //console.log(response);

        const reader = response.body.getReader();

        var isFinished = false;
        var counter = 0;
        while (!isFinished) {
            counter++;
            console.log("итерация " + counter)
            await reader.read().then(({done, value}) => {
                isFinished = done;
                if (done) {
                    return;
                }
                var element = JSON.parse(
                    new TextDecoder("utf-8").decode(value.subarray(5))
                );
                console.log(element)
                var text = `<div style="width: 100px ">
<p style="text-align: center;">${element.name}</p>
<div style="background-color: #${element.color.toString(16)};">
<div style="text-align: left;">${element.age}</div>
<div style="text-align: right;">${element.classNumber}</div>
</div>
</div>`
                var div = document.createElement('div')
                div.innerHTML = text.trim();
                document.body.append(div.firstChild)
            })
        }

    })
});

