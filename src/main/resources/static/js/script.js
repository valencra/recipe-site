$('#add-ingredient').click(function() {
    var ingredientCount = $('.ingredient-row').length;
    var ingredientRow =  '<div class="ingredient-row">' +
                         '<div class="prefix-20 grid-30">' +
                         '<p> <input type="text" id="ingredients' + index + '.item" name="ingredients[' + index + '].name" />' +
                         '</p> </div>' +
                         '<div class="grid-30">' +
                         '<p> <input type="text" id="ingredients' + index + '.condition" name="ingredients[' + index + '].condition" />' +
                         '</p> </div>' +
                         '<div class="grid-10 suffix-10">' +
                         '<p> <input type="text" id="ingredients' + index + '.quantity" name="ingredients[' + index + '].quantity" />' +
                         '</p> </div>' +
                         '</div>'
    $("#add-ingredient-row" ).before(newRow);
});

$('#add-step').click(function() {
    var stepCount = $('.step-row').length;
    var stepRow =  '<div class="step-row">' +
                   '<div class="prefix-20 grid-80">' +
                   '<p>' +
                   '<input  type="text" id="steps' + index + '.stepName" name="steps[' + index + '].stepName" />' +
                   '</p>' +
                   '</div>' +
                   '</div>'
    $("#add-step-row" ).before(newRow);
});