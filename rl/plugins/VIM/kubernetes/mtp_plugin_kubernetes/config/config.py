import xmltodict


def singleton(cls):
    instances = {}

    def getinstance(anyArgs=None):
        if cls not in instances:
            instances[cls] = cls(anyArgs)
        return instances[cls]

    return getinstance


@singleton
class ConfigurationFile:
    _config = {}

    def __init__(self, count=None):
        with open('config.xml') as conf:
            self._config = xmltodict.parse(conf.read())

    @property
    def config(self):
        return self._config
