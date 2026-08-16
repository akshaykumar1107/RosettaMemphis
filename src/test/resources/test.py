from typing import Any

import requests

text_list: list[str] = ['The sun rises in the east',
                        'She smiled at the stranger']

languages: list[str] = ['fr', 'de', 'es', 'it']
api_keys: list[str] = ['83e6289d-aab6-4c5f-bae1-c920b22f31ce', 'cae55c38-134c-48d8-8f68-1511f140bd61']# a paid plan's API key and a free plan's API key respectively.

for text in text_list:
    for language in languages:
        for api_key in api_keys:
            response: dict[str:Any] = requests.post(url='http://localhost:1107/api/v1/translate',
                          headers={'api-key': api_key},
                          json={
                                  "source_language":"en",
                                  "translation_language":language,
                                  "source_text":text
                                }
                          ).json()

            print(response)