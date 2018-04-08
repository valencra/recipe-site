$('#add-ingredient').click(function() {
    var ingredientCount = $('.ingredient-row').length;
    var ingredientRow =  '<div class="ingredient-row">' +
                         '<div class="prefix-20 grid-30">' +
                         '<p> <input type="text" id="ingredients' + ingredientCount + '.item" name="ingredients[' + ingredientCount + '].name" />' +
                         '</p> </div>' +
                         '<div class="grid-30">' +
                         '<p> <input type="text" id="ingredients' + ingredientCount + '.condition" name="ingredients[' + ingredientCount + '].condition" />' +
                         '</p> </div>' +
                         '<div class="grid-10 suffix-10">' +
                         '<p> <input type="text" id="ingredients' + ingredientCount + '.quantity" name="ingredients[' + ingredientCount + '].quantity" />' +
                         '</p> </div>' +
                         '</div>'
    $("#add-ingredient-row" ).before(ingredientRow);
});

$('#add-step').click(function() {
    var stepCount = $('.step-row').length;
    var stepRow =  '<div class="step-row">' +
                   '<div class="prefix-20 grid-80">' +
                   '<p>' +
                   '<input  type="text" id="steps' + stepCount + '.stepName" name="steps[' + stepCount + '].stepName" />' +
                   '</p>' +
                   '</div>' +
                   '</div>'
    $("#add-step-row" ).before(stepRow);
});