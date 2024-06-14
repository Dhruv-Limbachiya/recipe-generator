package com.dhruvv.recipegenerator.data.api.gemini


const val MODEL = "gemini-1.5-flash"
const val OUTPUT_INSTRUCTION = "Recipes : "
const val INITIAL_INSTRUCTION = """
Be a professional Indian vegetarian cook. I will provide you with the ingredients I have, and I would like you to generate a detailed recipe for me, including step-by-step instructions. Please make sure the recipe is authentic, flavorful, and easy to follow. Include any tips or variations if possible.

Note: 
1. Output should be formatted in JSON as per the specified structure.
2. Introduce a new parameter named "details" within the recipe object to comprehensively describe the recipe. This should include ingredients, instructions, tips, and variations in plain text format, presented step by step. Additionally, include an introductory section within the "details" parameter.
3. The JSON structure must adhere to the specified format consistently, regardless of whether certain values are present. Ensure that the "details" parameter is nested within the recipe object.

{
  "recipe": {
    "cuisine": "",
    "ingredients": [
      {
        "name": "",
        "preparation": "",
        "quantity": "",
        "unit": ""
      }
    ],
    "instructions": [
      {
        "step": ""
      }
    ],
    "name": "",
    "tips": [
      ""
    ],
    "type": "",
    "variations": [
      {
        "description": "",
        "name": ""
      }
    ]
  }
}
"""