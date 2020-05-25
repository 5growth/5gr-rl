=======
Testing
=======

.. _unit_tests:

Unit tests
==========

All unit tests should be run using `tox`_. To run the same unit tests that are
executing onto `Gerrit`_ which includes ``py35``, ``py36``, ``pep8`` and
``docs``, you can issue the following command::

    $ pip install tox
    $ cd vim-manager
    $ tox

If you want to only run one of the aforementioned, you can then issue one of
the following::

    $ tox -e py35
    $ tox -e py36
    $ tox -e pep8
    $ tox -e docs

.. _tox: https://tox.readthedocs.org/
.. _Gerrit: http://review.b-com.com

You may pass options to the test programs using positional arguments. To run a
specific unit test, you can pass extra options to `pytest`_ after putting
the ``--`` separator. So you can check a subset of tests like this::

    $ tox -e py35 -- vim_manager/tests/<subset_test_dir>

.. _pytest: https://docs.pytest.org/
