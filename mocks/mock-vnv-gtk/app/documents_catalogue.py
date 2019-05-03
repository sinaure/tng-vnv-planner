null = None
tests=[
  {
    "created_at": "2018-08-07T15:19:57.887+00:00",
    "md5": "e3b76782ee5c95a0dff01dd4c544f72d",
    "signature": null,
    "status": "active",
    "testd": {
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "vendor": "eu.5gtango.atos.simple-test-descriptor",
      "name": "http-benchmark-test",
      "version": "0.3",
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking",
      "test_type": "bash",
      "validate": "true",
      "test_configuration_parameters": [
        {
          "parameter_name": "config_file",
          "parameter_definition": "location of the test file",
          "parameter_value": "config.cfg",
          "content_type": "text/plain"
        },
        {
          "parameter_name": "runner_file",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_value": "runner.sh",
          "content_type": "text/plain"
        }
      ],
      "test_category": [
        {
          "category_type": "performance"
        }
      ]
    },
    "updated_at": "2018-08-07T15:19:57.887+00:00",
    "updated_at": "2018-08-07T15:19:57.887+00:00",
    "testing_tags": [
      "http",
      "latency"
    ],
    "uuid": "input0ts-75f5-4ca1-90c8-12ec80a79836"
  },
  {
    "created_at": "2018-08-07T15:19:57.887+00:00",
    "md5": "e3b76782ee5c95a0dff01dd4c544f72d",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "http-benchmark-test",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "validate": "true",
      "vendor": "eu.5gtango.atos.simple-test-descriptor",
      "version": "0.3"
    },
    "updated_at": "2018-08-07T15:19:57.887+00:00",
    "username": "paco",
    "testing_tags": [
      "http",
      "latency"
    ],
    "uuid": "9bbbd636-75f5-4ca1-90c8-12ec80a79875"
  },
  {
    "created_at": "2018-08-07T15:13:26.118+00:00",
    "md5": "f69b843bf9d5fffbd4a55ca9c5d08c08",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "http-benchmark-test",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "validate": "true",
      "vendor": "eu.5gtango.atos.simple-test-descriptor",
      "version": "0.2"
    },
    "updated_at": "2018-08-07T15:13:26.118+00:00",
    "testing_tags": [
      "http",
      "latency"
    ],
    "uuid": "ccbf8bad-2534-4308-b47c-4034133b3116"
  },
  {
    "created_at": "2018-08-05T10:00:17.184+00:00",
    "md5": "ced22360c28972a908c709efa72f63e2",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "http-benchmark-test",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "validate": "false",
      "vendor": "eu.5gtango.atos.simple-test-descriptor",
      "version": "0.1"
    },
    "updated_at": "2018-08-05T10:00:17.184+00:00",
    "testing_tags": [
      "http",
      "latency"
    ],
    "uuid": "fe7ec2a8-644f-4788-9aa7-bc2ff0598157"
  },
  {
    "created_at": "2018-09-11T07:23:04.942+00:00",
    "executions": "7",
    "last_time_executed": "2018-09-12T09:15:48.410+00:00",
    "md5": "0cc5f876dad4fa49ebab8714356a054b",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking advanced proxy",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "test-http-benchmark-advanced-proxy",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "validate": "1",
      "vendor": "eu.5gtango.atos",
      "version": "0.1"
    },
    "updated_at": "2018-09-11T07:23:04.942+00:00",
    "testing_tags": [
      "proxy-advanced"
    ],
    "uuid": "b68dbe19-5c02-4865-8c4b-5e43ada1b67d"
  },
  {
    "created_at": "2018-09-11T07:23:04.942+00:00",
    "executions": "7",
    "last_time_executed": "2018-09-12T09:15:48.410+00:00",
    "md5": "0cc5f876dad4fa49ebab8714356a054b",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking advanced proxy",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "test-http-benchmark-advanced-proxy",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "vendor": "eu.5gtango.atos",
      "version": "0.2"
    },
    "updated_at": "2018-09-11T07:23:04.942+00:00",
    "confirm_required": "1",
    "validate": "1",
    "testing_tags": [
      "proxy-advanced"
    ],
    "uuid": "b68dbe19-5c02-4865-8c4b-5e43ada1b67c"
  },
  {
    "created_at": "2018-09-11T07:23:04.942+00:00",
    "executions": "7",
    "last_time_executed": "2018-09-12T09:15:48.410+00:00",
    "md5": "0cc5f876dad4fa49ebab8714356a054b",
    "signature": null,
    "status": "confirmed",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking advanced proxy",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "test-http-benchmark-advanced-proxy",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "validate": "1",
      "vendor": "eu.5gtango.atos",
      "version": "0.2"
    },
    "updated_at": "2018-09-11T07:23:04.942+00:00",
    "confirm_required": "1",
    "confirmed": "1",
    "testing_tags": [
      "proxy-advanced"
    ],
    "uuid": "b68dbe19-5c02-4865-8c4b-5e43ada1b67b"
  },
  {
    "created_at": "2018-09-11T07:51:37.810+00:00",
    "executions": "14",
    "last_time_executed": "2018-09-12T13:05:35.141+00:00",
    "md5": "090ae81a44104fd7470e92132a804215",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Felipe Vicens, ATOS",
      "description": "Test descriptor for http benchmarking advanced",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "test-http-benchmark-advanced",
      "test_category": [
        {
          "category_type": "performance"
        }
      ],
      "test_configuration_parameters": [
        {
          "content_type": "text/plain",
          "parameter_definition": "location of the test file",
          "parameter_name": "config_file",
          "parameter_value": "config.cfg"
        },
        {
          "content_type": "text/plain",
          "parameter_definition": "bash script to run the http benchmark test within docker container",
          "parameter_name": "runner_file",
          "parameter_value": "runner.sh"
        }
      ],
      "test_type": "bash",
      "vendor": "eu.5gtango.atos",
      "version": "0.1"
    },
    "updated_at": "2018-09-11T07:51:37.810+00:00",
    "testing_tags": [
      "http-advanced"
    ],
    "uuid": "aa5c779a-8cc7-47a9-9112-d2ff348898b4"
  },
  {
    "created_at": "2019-04-28T07:51:37.810+00:00",
    "executions": "14",
    "last_time_executed": "2019-04-28T10:51:37.810+00:00",
    "md5": "090ae81a44aa4fd7470e92i32a8aw215",
    "signature": null,
    "status": "active",
    "testd": {
      "author": "Ignacio Dominguez, Felipe Vicens (ATOS)",
      "description": "Performance test for video analysis",
      "descriptor_schema": "https://raw.githubusercontent.com/sonata-nfv/tng-schema/master/test-descriptor/testdescriptor-schema.yml",
      "name": "test-immersive-media",
      "vendor": "eu.5gtango.atos",
      "version": "0.1",
      "phases": [
        {
          "id": "setup",
          "steps": [
            {
              "action": "deploy",
              "description": "Deploying a NS",
              "name": "deployment"
            },
            {
              "action": "configure",
              "description": null,
              "name": "configuration",
              "probes": [
                {
                  "description": "A service initial configuration container",
                  "id": "initiator",
                  "image": "ignaciodomin/media-initiator:dev",
                  "name": "initiator",
                  "parameters": [
                    {
                      "key": "CAMERA",
                      "value": "test"
                    },
                    {
                      "key": "CMS",
                      "value": "$(vnf-cms/endpoints/id:floating-ip/address)"
                    },
                    {
                      "key": "MA",
                      "value": "$(vnf-ma/endpoints/id:floating-ip/address)"
                    },
                    {
                      "key": "MSE",
                      "value": "$(vnf-mse/endpoints/id:floating-ip/address)"
                    }
                  ]
                },
                {
                  "id": "ffprobe",
                  "name": "ffprobe",
                  "image": "ignaciodomin/media-ffprobe:dev",
                  "description": "Ffprobe",
                  "parameters": [
                    {
                      "key": "STREAMING_ENGINE",
                      "value": "$(vnf-mse/endpoints/id:floating-ip/address)"
                    },
                    {
                      "key": "STREAM",
                      "value": "plane"
                    }
                  ]
                },
                {
                  "id": "parser",
                  "name": "parser",
                  "image": "ignaciodomin/media-parser:dev",
                  "description": "Parser",
                  "parameters": [
                  ]
                },
                {
                  "description": "Content Producer Emulator (CPE) To generate a RTMP flow",
                  "id": "cpe",
                  "image": "ignaciodomin/media-cpe:plane",
                  "name": "cpe",
                  "parameters": [
                    {
                      "key": "AGGREGATOR",
                      "value": "$(vnf-ma/endpoints/id:floating-ip/address)"
                    },
                    {
                      "key": "APP",
                      "value": "test"
                    },
                    {
                      "key": "STREAM",
                      "value": "test"
                    }
                  ]
                },
                {
                  "description": "Content Consumer Emulator (CCE) To play HLS flows from Streaming engine",
                  "id": "cce",
                  "image": "ignaciodomin/media-cce:dev",
                  "name": "cce",
                  "parameters": [
                    {
                      "key": "STREAMMING_ENGINE",
                      "value": "$(vnf-mse/endpoints/id:floating-ip/address)"
                    },
                    {
                      "key": "STREAM",
                      "value": "test"
                    }
                  ]
                }
              ]
            }
          ]
        },
        {
          "id": "exercise",
          "steps": [
            {
              "name": "cpe",
              "description": "Starting the CPE that simulates the camera",
              "run": "cpe",
              "index": 1,
              "entrypoint": "/app/entrypoint.sh",
              "command": "/bin/sh",
              "instances": 1,
              "output": [
                {
                  "results": "logs.txt"
                }
              ]
            },
            {
              "name": "ffprobe",
              "description": "Starting the CCE that simulates the consumer",
              "run": "ffprobe",
              "index": 2,
              "entrypoint":"/app/entrypoint.sh",
              "start_delay": 5,
              "instances": 1,
              "output": [
                {
                  "results": "logs.txt"
                }
              ]
            },
            {
              "name": "cce",
              "description": "Starting the CCE that simulates the consumer",
              "run": "cce",
              "index": 3,
              "entrypoint":"/app/entrypoint.sh",
              "start_delay": 20,
              "instances": 1,
              "output": [
                {
                  "results": "logs.txt"
                }
              ]
            },
            {
              "name": "parser",
              "description": "Starting the CCE that simulates the consumer",
              "run": "parser",
              "index": 4,
              "entrypoint":"/app/entrypoint.sh",
              "instances": 1,
              "output": [
                {
                  "results": "logs.txt"
                }
              ],
              "dependencies": [
                "cpe",
                "ffprobe",
                "cce"
              ]
            }
          ]
        },
        {
          "id": "verification",
          "steps": [
            {
              "name": "cpe",
              "description": "Check cpe",
              "step": "cpe",
              "conditions": [
                {
                  "name": "status",
                  "file": "status.txt",
                  "find": "ERROR",
                  "condition": "not present",
                  "verdict": "pass"
                }
              ]
            },
            {
              "name": "cce",
              "description": "Check cce",
              "step": "cce",
              "conditions": [
                {
                  "name": "status",
                  "file": "status.txt",
                  "find": "ERROR",
                  "condition": "not present",
                  "verdict": "pass"
                }
              ]
            },
            {
              "name": "ffprobe",
              "description": "Check ffprobe",
              "step": "ffprobe",
              "conditions": [
                {
                  "name": "status",
                  "file": "status.txt",
                  "find": "ERROR",
                  "condition": "not present",
                  "verdict": "pass"
                }
              ]
            },
            {
              "name": "parser",
              "description": "Check obtained results",
              "step": "parser",
              "conditions": [
                {
                  "name": "lost-frames",
                  "file": "result.json",
                  "type": "json",
                  "find": "lost-frames",
                  "condition": "<",
                  "value": "90",
                  "verdict": "pass"
                }
              ]
            }
          ]
        }
      ],
      "service_platforms": [
        "SONATA"
      ],
      "test_category": [
        "benchmarking"
      ]
    },

    "updated_at": "2019-03-28T09:51:37.810+00:00",
    "testing_tags": [
      "rtmp-media-service"
    ],
    "uuid": "immedia0-8cc7-47a9-9112-6wff9e88wu2k"
  }
]
