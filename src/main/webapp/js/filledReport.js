document.addEventListener("DOMContentLoaded", () => {
    // Add Row Button Functionality
    const addRowButton = document.getElementById("addRowButton");
    const exercisesTable = document.getElementById("trainingTable");

    addRowButton.addEventListener("click", () => {
        // Create a new row
        const newRow = document.createElement("tr");

        // Define the columns for the new row
        newRow.innerHTML = `
            <td><input type="text" name="new_exercise_name[]" placeholder="Enter Exercise"></td>
            <td><input type="number" name="new_reps[]" placeholder="Reps"></td>
            <td><input type="number" name="new_sets[]" placeholder="Sets"></td>
            <td><input type="date" name="new_exercise_date[]"></td>
            <td><input type="text" name="new_rest[]" placeholder="Rest"></td>
            <td><input type="number" name="new_weight[]" placeholder="Weight"></td>
        `;

        // Append the new row to the table
        exercisesTable.querySelector("tbody").appendChild(newRow);
    });
});
