function loadUserStat() {
    var request = new XMLHttpRequest();
    request.open('get', '/contentProvider/api/category-statistic', true)
    request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var response = JSON.parse(request.responseText);
                handleLoadUsersResponse(response);
            } else {
                console.log("Failed to load users stat: ", request)
            }
        }
    };
    request.send(null);
}

function handleLoadUsersResponse(response) {
    document.getElementById('all').textContent = response.countAllCategories;
    document.getElementById('public').textContent = response.countPublicCategories;
    document.getElementById('private').textContent = response.countPrivateCategories;
    document.getElementById('all-articles').textContent = response.countArticlesInAllCategories;
    document.getElementById('articles-in-public').textContent = response.countArticlesInPublicCategories;
    document.getElementById('articles-in-private').textContent = response.countArticlesInPrivateCategories;
}